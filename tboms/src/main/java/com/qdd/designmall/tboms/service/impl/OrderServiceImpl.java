package com.qdd.designmall.tboms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.*;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.CustomerServiceOrderInputPo;
import com.qdd.designmall.tboms.po.PageCsOrderPo;
import com.qdd.designmall.tboms.po.PageIgOrderPo;
import com.qdd.designmall.tboms.po.PageWriterOrderPo;
import com.qdd.designmall.tboms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DbTbomsWriterOrderService dbTbomsWriterOrderService;
    private final SecurityUserService securityUserService;
    private final DbTbomsCustomerServiceOrderService dbTbomsCustomerServiceOrderService;
    private final DbTbomsIntegratedOrderService dbTbomsIntegratedOrderService;
    private final DbShopUserRelationService dbShopUserRelationService;
    private final DbSmsShopService dbSmsShopService;


    @Override
    @Transactional
    public void input(CustomerServiceOrderInputPo param) {
        Long shopId = param.getShopId();
        Long userId = getCurrentUserId();

        // 验证数据
        inputValidate(param, userId);


        // 计算订单总价
        BigDecimal integratedOrderPriceAmount = BigDecimal.ZERO;
        for (var taobaoOrder : param.getTaobaoOrders()) {
            integratedOrderPriceAmount = integratedOrderPriceAmount.add(taobaoOrder.getOrderPrice());
        }


        // 计算客服佣金
        BigDecimal csCommissionRate = dbShopUserRelationService.getCsCommissionRate(shopId, userId);
        BigDecimal csCommission = integratedOrderPriceAmount.multiply(csCommissionRate);


        // 计算应付写手工资
        BigDecimal integratedShouldPayAmount = BigDecimal.ZERO;
        for (var writer : param.getWriters()) {
            integratedShouldPayAmount = integratedShouldPayAmount.add(writer.getShouldPay());
        }


        // 创建综合订单
        DbTbomsIntegratedOrder integratedOrder = new DbTbomsIntegratedOrder();
        integratedOrder.setShopId(shopId);
        integratedOrder.setUpdaterId(userId);
        integratedOrder.setTaobaoOrderNo(param.getTaobaoOrders().getFirst().getOrderNo());  // 订单编号
        integratedOrder.setOrderPriceAmount(integratedOrderPriceAmount);                    // 订单总价
        integratedOrder.setShouldPayAmount(integratedShouldPayAmount);                      // 应付写手工资总额
//        integratedOrder.setProfileMargin(param.getProfileMargin());                         // 利润率
        integratedOrder.setCsCommission(csCommission);                                       // 客服佣金
        integratedOrder.setCsCommissionRate(csCommissionRate);                               // 客服佣金率
        dbTbomsIntegratedOrderService.save(integratedOrder);
        Long integratedOrderId = integratedOrder.getId();       // 自动生成的id


        // 创建客服订单
        List<DbTbomsCustomerServiceOrder> list = param.getTaobaoOrders().stream().map(e -> {
            DbTbomsCustomerServiceOrder entity = new DbTbomsCustomerServiceOrder();
            entity.setShopId(shopId);
            entity.setUpdaterId(userId);
            entity.setCreateAt(LocalDateTime.now());
            entity.setUpdateAt(LocalDateTime.now());
            entity.setOrderPriceAmount(e.getOrderPrice());
            entity.setTaobaoOrderNo(e.getOrderNo());
            entity.setIntegratedOrderId(integratedOrderId);
            return entity;
        }).toList();
        dbTbomsCustomerServiceOrderService.saveBatch(list);


        // 创建写手订单
        List<DbTbomsWriterOrder> list1 = param.getWriters().stream().map(e -> {
            DbTbomsWriterOrder entity = new DbTbomsWriterOrder();
            entity.setShopId(shopId);
            entity.setUpdaterId(userId);
            entity.setIntegratedOrderId(integratedOrderId);
            entity.setWriterId(e.getWriterId());
            entity.setOrderState(0);
            entity.setShouldPay(e.getShouldPay());
            entity.setCreateAt(LocalDateTime.now());
            entity.setUpdateAt(LocalDateTime.now());
            return entity;
        }).toList();
        dbTbomsWriterOrderService.saveBatch(list1);
    }


    /**
     * 输入验证，包括：
     * <ol">
     * <li>用户有权限添加订单</li>
     * <li>订单和写手数量不为0</li>
     * <li>写手在该店铺</li>
     * <li>与已有的淘宝订单不重复</li>
     * </ol>
     */
    private void inputValidate(CustomerServiceOrderInputPo param, Long currentUserId) {
        // 验证当前可以修改订单
        Long shopId = param.getShopId();
        ableToModifyOrder(currentUserId, shopId);


        // 验证订单数量 > 0
        if (param.getTaobaoOrders().isEmpty()) {
            throw new RuntimeException("订单不能为空");
        }
        // 写手数量>0
        if (param.getWriters().isEmpty()) {
            throw new RuntimeException("写手不能为空");
        }


        // 验证写手在该店铺
        for (var writer : param.getWriters()) {
            Long writerId = writer.getWriterId();
            dbShopUserRelationService.notExistsThrow(shopId, writerId, 2);
        }


        // 验证订单没有重复
        for (var taobaoOrder : param.getTaobaoOrders()) {
            String orderNo = taobaoOrder.getOrderNo();
            dbTbomsCustomerServiceOrderService.existThrow(orderNo, shopId);
        }
    }

    /**
     * 用户可以修改订单（是店长或客服）
     *
     * @param userId 用户id
     * @param shopId 店铺id
     */
    private void ableToModifyOrder(Long userId, Long shopId) {
        DbShopUserRelation entity = dbShopUserRelationService.getNullableOne(shopId, userId);
        if (entity != null) {
            Integer relation = entity.getRelation();
            if (relation == 0 || relation == 1) return;     // 是店长或客服

        }
        throw new RuntimeException("当前用户无权修改订单");
    }


    @Override
    public IPage<DbTbomsIntegratedOrder> pageIntegratedOrder(PageIgOrderPo param, int relation) {
        Long userId = getCurrentUserId();
        switch (relation) {
            case 0 -> {
                // 是店长，返回所有订单
                dbSmsShopService.notExistsThrow(param.getShopId(), userId);
                return dbTbomsIntegratedOrderService.page(param.getPage().iPage(), param.getShopId(), null);
            }
            case 1 -> {
                // 是客服，返回自己的订单
                dbShopUserRelationService.notExistsThrow(param.getShopId(), userId, 1);
                return dbTbomsIntegratedOrderService.page(param.getPage().iPage(), param.getShopId(), userId);
            }
            default -> throw new RuntimeException("关系错误");
        }
    }

    @Override
    public IPage<DbTbomsCustomerServiceOrder> pageCsOrder(PageCsOrderPo param, int relation) {
        Long userId = getCurrentUserId();
        Long igOrderId = param.getIgOrderId();
        Long shopId = dbTbomsIntegratedOrderService.getNotNullShopIdById(igOrderId);
        switch (relation) {
            case 0 -> {
                // 是店长，返回所有订单
                dbSmsShopService.notExistsThrow(shopId, userId);
                return dbTbomsCustomerServiceOrderService.page(param.getPage().iPage(), igOrderId, null);
            }
            case 1 -> {
                // 是客服，返回自己的订单
                dbShopUserRelationService.notExistsThrow(igOrderId, userId, 1);
                return dbTbomsCustomerServiceOrderService.page(param.getPage().iPage(), igOrderId, userId);
            }
            default -> throw new RuntimeException("关系错误");
        }
    }

    @Override
    public IPage<DbTbomsWriterOrder> pageWriterOrder(PageWriterOrderPo param, int relation) {
        Long userId = getCurrentUserId();
        Long igOrderId = param.getIgOrderId();
        Long shopId = dbTbomsIntegratedOrderService.getNotNullShopIdById(igOrderId);
        switch (relation) {
            case 0 -> {
                // 是店长，返回所有订单
                dbSmsShopService.notExistsThrow(shopId, userId);
                return dbTbomsWriterOrderService.page(param.getPage().iPage(), igOrderId, null);
            }
            case 1 -> {
                // 是客服，返回自己的订单
                dbShopUserRelationService.notExistsThrow(igOrderId, userId, 1);
                return dbTbomsWriterOrderService.page(param.getPage().iPage(), igOrderId, userId);
            }
            default -> throw new RuntimeException("关系错误");
        }
    }

    @Override
    public void updateIgOrderState(Long igOrderId, int orderState) {
        var igOrder = dbTbomsIntegratedOrderService.getById(igOrderId);
        if (igOrder == null) {
            throw new RuntimeException("综合订单不存在");
        }
        updateIgOrderState(igOrder, orderState);
    }

    @Override
    public void updateIgOrderState(@NonNull DbTbomsIntegratedOrder igOrder, int orderState) {
        // 锁定状态无法修改
        if (igOrder.getLock().equals(1)) {
            throw new RuntimeException("订单已锁定，无法更新状态");
        }

        // 更新综合订单状态
        igOrder.setOrderState(orderState);

        // 更新相关写手订单状态
        List<DbTbomsWriterOrder> writerOrders = dbTbomsWriterOrderService.lambdaQuery()
                .eq(DbTbomsWriterOrder::getIntegratedOrderId, igOrder.getId())
                .list();
        writerOrders = writerOrders.stream().peek(e -> e.setOrderState(orderState)).toList();

        // 更新到数据库
        dbTbomsWriterOrderService.updateBatchById(writerOrders);
        dbTbomsIntegratedOrderService.updateById(igOrder);
    }

    private Long getCurrentUserId() {
        return securityUserService.currentUserDetails().getUserId();
    }
}
