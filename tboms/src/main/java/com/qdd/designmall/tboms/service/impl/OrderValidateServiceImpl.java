package com.qdd.designmall.tboms.service.impl;

import com.qdd.designmall.mbp.mapper.DbTbomsIntegratedOrderMapper;
import com.qdd.designmall.mbp.model.*;
import com.qdd.designmall.mbp.service.*;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.OrderValidateConditionPo;
import com.qdd.designmall.tboms.service.OrderValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderValidateServiceImpl implements OrderValidateService {
    private final DbSmsShopService dbSmsShopService;
    private final SecurityUserService securityUserService;
    private final DbTbomsIntegratedOrderMapper dbTbomsIntegratedOrderMapper;
    private final DbTbomsTaobaoOrderService dbTbomsTaobaoOrderService;
    private final DbTbomsCustomerServiceOrderService dbTbomsCustomerServiceOrderService;
    private final DbTbomsWriterOrderService dbTbomsWriterOrderService;
    private final DbTbomsIntegratedOrderService dbTbomsIntegratedOrderService;
    private final DbTbomsIntegratedOrderValidationHistoryService historyService;


    public static final Map<String, Integer> OrderStateMap = Map.of(
            "交易关闭", 0,
            "买家已下单，未付款", 1,
            "买家已付款，等待卖家发货", 2,
            "卖家已发货，等待买家确认", 3,
            "交易成功", 4
    );


    @Override
    public Map<Long, Map<String, Object>> validate(OrderValidateConditionPo param) {
        Long userId = securityUserService.currentUserDetails().getUserId();
        Long shopId = param.getShopId();
        // 当前用户是该店铺店长
        dbSmsShopService.notExistsThrow(shopId, userId);

        // 获取综合订单
        var integratedOrders = dbTbomsIntegratedOrderMapper.queryByCondition(shopId, param.getStartTime(), param.getEndTime());
        if (integratedOrders.isEmpty()) return null;


        // 获取关联的客服订单
        List<Long> integratedOrderIds = integratedOrders.stream().map(DbTbomsIntegratedOrder::getId).distinct().toList();
        var csOrders = dbTbomsCustomerServiceOrderService.listInIntegratedOrderIds(shopId, integratedOrderIds);
        if (csOrders.isEmpty()) return null;


        // 获取关联的淘宝订单
        List<String> taobaoOrderNos = csOrders.stream().map(DbTbomsCustomerServiceOrder::getTaobaoOrderNo).distinct().toList();
        var taobaoOrderList = dbTbomsTaobaoOrderService.listInTaobaoOrderNos(shopId, taobaoOrderNos);
        if (taobaoOrderList.isEmpty()) return null;
        Map<String, DbTbomsTaobaoOrder> taobaoOrderMap = taobaoOrderList.stream().collect(Collectors.toMap(DbTbomsTaobaoOrder::getTaobaoOrderNo, o -> o));


        // 根据淘宝订单修改客服订单状态
        updateCsOrders(csOrders, taobaoOrderMap);


        // ********** 修改综合订单的值和状态 **********
        // 客服订单映射
        var csOrderMap = csOrders.stream().collect(Collectors.groupingBy(DbTbomsCustomerServiceOrder::getIntegratedOrderId));
        // 写手订单
        var dbTbomsWriterOrders = dbTbomsWriterOrderService.listByShopIdAndInIntegratedOrderIds(shopId, integratedOrderIds);
        var writerOrderMap = dbTbomsWriterOrders.stream().collect(Collectors.groupingBy(DbTbomsWriterOrder::getIntegratedOrderId));
        updateIntegratedOrders(integratedOrders, writerOrderMap, csOrderMap);


        // 更新客服订单到数据库
        dbTbomsCustomerServiceOrderService.updateBatchById(csOrders);


        // 更新综合订单到数据库（只保存未锁定订单）
        List<DbTbomsIntegratedOrder> unLockedIgOrders = integratedOrders.stream()
                .filter(e -> e.getLock().equals(0))                                 // 筛选未锁定订单
                .peek(e -> e.setValidationVersion(UUID.randomUUID().toString()))    // 生成新的校验版本
                .toList();
        dbTbomsIntegratedOrderService.updateBatchById(unLockedIgOrders);

        // 添加综合订单状态历史到数据库
        saveToHistory(unLockedIgOrders);

        //TODO 更新写手订单到数据库

        return integratedOrders.stream().collect(Collectors.toMap(DbTbomsIntegratedOrder::getId, o -> Map.of(
                "integratedOrder", o,
                "csOrders", csOrderMap.get(o.getId()),
                "writerOrders", writerOrderMap.get(o.getId())
        )));
    }

    /**
     * 将综合订单状态历史保存到数据库
     *
     * @param integratedOrders 综合订单
     */
    private void saveToHistory(List<DbTbomsIntegratedOrder> integratedOrders) {
        LocalDateTime now = LocalDateTime.now();
        List<DbTbomsIntegratedOrderValidationHistory> list = integratedOrders.stream().map(e -> {
            DbTbomsIntegratedOrderValidationHistory entity = new DbTbomsIntegratedOrderValidationHistory();
            entity.setIntegratedOrderId(e.getId());
            entity.setOrderValidatedState(e.getOrderValidatedState());
            entity.setTaobaoOrderState(e.getTaobaoOrderState());
            entity.setHasCorrespondingTaobaoOrderState(e.getHasCorrespondingTaobaoOrderState());
            entity.setPriceAmountRightState(e.getPriceAmountRightState());
            entity.setPayAmountRightState(e.getPayAmountRightState());
            entity.setValidationAt(now);
            return entity;
        }).toList();

        historyService.saveBatch(list);
    }


    /**
     * 更新综合订单信息
     *
     * @param integratedOrders 待更新综合订单
     * @param writerOrderMap   写手订单映射
     * @param csOrderMap       客服订单映射
     */
    private static void updateIntegratedOrders(List<DbTbomsIntegratedOrder> integratedOrders, Map<Long, List<DbTbomsWriterOrder>> writerOrderMap, Map<Long, List<DbTbomsCustomerServiceOrder>> csOrderMap) {
        for (var integratedOrder : integratedOrders) {
            var writerOrders = writerOrderMap.get(integratedOrder.getId());
            var cusOrders = csOrderMap.get(integratedOrder.getId());

            // 客服订单中有没有对应的淘宝订单，不更新此综合订单
            if (cusOrders.stream().anyMatch(e -> e.getHasCorrespondingTaobaoOrderState() == 0)) {
                continue;
            }

            boolean taobaoOrderState = true;
            BigDecimal orderPriceAmount = BigDecimal.ZERO;
            BigDecimal taobaoOrderPriceAmount = BigDecimal.ZERO;
            boolean priceAmountRightState = true;
            for (var cusOrder : cusOrders) {

                // 淘宝订单状态有任一不是交易完成的，设为false
                if (cusOrder.getTaobaoOrderState() != 4) {
                    taobaoOrderState = false;
                }

                // 订单金额总和
                orderPriceAmount = orderPriceAmount.add(cusOrder.getOrderPriceAmount());
                // 淘宝订单金额总和
                taobaoOrderPriceAmount = taobaoOrderPriceAmount.add(cusOrder.getTaobaoOrderPriceAmount());


                // 有任一客服订单金额不正确，订单金额设为false
                if (cusOrder.getPriceAmountRightState() == 0) {
                    priceAmountRightState = false;
                }
            }


            // 累加应付工资
            BigDecimal shouldPayAmount = writerOrders.stream().map(DbTbomsWriterOrder::getShouldPay).reduce(BigDecimal.ZERO, BigDecimal::add);


            // 支付金额正确： （订单金额-应付工资）/ 订单金额 == 利润率
            boolean payAmountRightState =
                    orderPriceAmount.subtract(shouldPayAmount)
                            .divide(orderPriceAmount, 3, RoundingMode.HALF_UP)
                            .compareTo(integratedOrder.getProfileMargin()) >= 0;


            integratedOrder.setTaobaoOrderState(taobaoOrderState ? 1 : 0);              // 淘宝订单交易成功
            integratedOrder.setHasCorrespondingTaobaoOrderState(1);                     // 有对应的淘宝订单
            integratedOrder.setOrderPriceAmount(orderPriceAmount);                      // 订单金额
            integratedOrder.setTaobaoOrderPriceAmount(taobaoOrderPriceAmount);          // 淘宝订单金额
            integratedOrder.setPriceAmountRightState(priceAmountRightState ? 1 : 0);    // 订单金额正确
            integratedOrder.setShouldPayAmount(shouldPayAmount);                        // 应付工资
            integratedOrder.setPayAmountRightState(payAmountRightState ? 1 : 0);        // 支付金额正确
            integratedOrder.setOrderValidatedState(taobaoOrderState && priceAmountRightState && payAmountRightState ? 1 : -1);  // 验证成功状态
        }
    }


    /**
     * 根据淘宝订单更新客服订单状态
     *
     * @param csOrders       待更新客服订单
     * @param taobaoOrderMap 淘宝订单映射
     */
    private static void updateCsOrders(List<DbTbomsCustomerServiceOrder> csOrders, Map<String, DbTbomsTaobaoOrder> taobaoOrderMap) {
        for (var csOrder : csOrders) {
            String taobaoOrderNo = csOrder.getTaobaoOrderNo();

            if (taobaoOrderMap.containsKey(taobaoOrderNo)) {
                DbTbomsTaobaoOrder taobaoOrder = taobaoOrderMap.get(taobaoOrderNo);

                csOrder.setTaobaoOrderState(OrderStateMap.get(taobaoOrder.getTaobaoOrderStatus()));     // 订单状态，需要和淘宝订单对应
                csOrder.setHasCorrespondingTaobaoOrderState(1); // 有对应的淘宝订单
                BigDecimal taobaoOrderPrice = taobaoOrder.getTaobaoBuyerActualPaymentAmount();
                csOrder.setTaobaoOrderPriceAmount(taobaoOrderPrice);     // 淘宝订单金额
                csOrder.setPriceAmountRightState(csOrder.getOrderPriceAmount().equals(taobaoOrderPrice) ? 1 : 0);   // 订单金额与淘宝订单金额相等
            }
        }
    }
}
