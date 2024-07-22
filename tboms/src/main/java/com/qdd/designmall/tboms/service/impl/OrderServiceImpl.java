package com.qdd.designmall.tboms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.*;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.CustomerServiceOrderInputPo;
import com.qdd.designmall.tboms.po.PageIgOrderPo;
import com.qdd.designmall.tboms.service.OrderService;
import com.qdd.designmall.tboms.po.PageCsOrderPo;
import com.qdd.designmall.tboms.po.PageWriterOrderPo;
import lombok.RequiredArgsConstructor;
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
        integratedOrder.setProfileMargin(param.getProfileMargin());                         // 利润率
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
            entity.setShouldPay(e.getShouldPay());
            entity.setCreateAt(LocalDateTime.now());
            entity.setUpdateAt(LocalDateTime.now());
            return entity;
        }).toList();
        dbTbomsWriterOrderService.saveBatch(list1);
    }


    /**
     * 输入验证
     */
    private void inputValidate(CustomerServiceOrderInputPo param, Long currentUserId) {
        // 验证客服属于该店铺
        Long shopId = param.getShopId();
        dbShopUserRelationService.notExistsThrow(shopId, currentUserId, 1);


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

    private Long getCurrentUserId() {
        return securityUserService.currentUserDetails().getUserId();
    }
}
