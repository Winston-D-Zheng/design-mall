package com.qdd.designmall.malloms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.malloms.po.OrderPageParam;
import com.qdd.designmall.malloms.service.OrderService;
import com.qdd.designmall.malloms.util.OrderSnGenUtil;
import com.qdd.designmall.malloms.vo.OrderPageVo;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.DbOmsOrderItem;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.service.DbOmsOrderItemService;
import com.qdd.designmall.mbp.service.DbOmsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DbOmsOrderService dbOmsOrderService;
    private final DbOmsOrderItemService dbOmsOrderItemService;

    @Override
    public DbOmsOrder orderAdd(PmsProduct product, Long memberId) {
        DbOmsOrder dbOmsOrder = new DbOmsOrder();
        dbOmsOrder.setOrderSn(OrderSnGenUtil.generate());         // 生成订单号
        dbOmsOrder.setProductId(product.getId());                 // 商品id
        dbOmsOrder.setShopId(product.getShopId());                // 店铺id
        dbOmsOrder.setProductPic(product.getPic());               // 商品图片
        dbOmsOrder.setProductName(product.getName());             // 商品名
        dbOmsOrder.setProductPrice(product.getOriginalPrice());   // 商品价格
        dbOmsOrder.setRealPrice(product.getPrice());              // 真实价格
        dbOmsOrder.setProductAttr("{}");                          // 商品属性
        dbOmsOrder.setMemberId(memberId);                         // 用户
        dbOmsOrder.setCreateTime(LocalDateTime.now());            // 创建时间
        dbOmsOrder.setEndStage(5);                                // 结束阶段
        dbOmsOrder.setCurrentStage(0);                            // 当前阶段
        dbOmsOrder.setStatus(0);                                  // 状态：待付款
        dbOmsOrder.setDeleteStatus(0);                            // 状态：未删除
        dbOmsOrder.setCompleteTime(null);                         // 订单完成时间

        // 插入订单表
        dbOmsOrderService.save(dbOmsOrder);

        return dbOmsOrder;
    }


    @Override
    public void orderItemAdd(Long orderId, Integer stage, BigDecimal price) {
        DbOmsOrderItem entity = new DbOmsOrderItem();
        entity.setOrderId(orderId);
        entity.setStage(stage);
        entity.setPrice(price);
        entity.setCreateTime(LocalDateTime.now());
        dbOmsOrderItemService.save(entity);

        // 更新订单stage和price
        DbOmsOrder order = dbOmsOrderService.notNullOne(orderId);
        order.setCurrentStage(stage);
        dbOmsOrderService.updateById(order);
    }

    @Override
    public void orderItemChangePrice(Long orderItemId, BigDecimal price) {
        DbOmsOrderItem orderItem = dbOmsOrderItemService.notNullOne(orderItemId);
        orderItem.setPrice(price);
        dbOmsOrderItemService.updateById(orderItem);
    }

    @Override
    public Optional<Long> orderItemPay(Long orderItemId) {
        Optional<Long> productMinus = Optional.empty();
        //TODO 金融方面的验证

        DbOmsOrderItem orderItem = dbOmsOrderItemService.notNullOne(orderItemId);
        DbOmsOrder order = dbOmsOrderService.notNullOne(orderItem.getOrderId());

        // 最后阶段子订单完成，订单整体设为完成
        if (Objects.equals(orderItem.getStage(), order.getEndStage())) {
            order.setStatus(2);
            productMinus = Optional.of(order.getProductId());
        }

        // 订单价格=订单项价格+订单项价格
        order.setRealPrice(order.getRealPrice().add(orderItem.getPrice()));

        order.setStatus(1);

        dbOmsOrderService.updateById(order);
        dbOmsOrderItemService.updateById(orderItem);

        return productMinus;
    }

    @Override
    public IPage<OrderPageVo> pageOrder(OrderPageParam param, EUserType userType) {

        switch (userType) {
            case ADMIN -> {
                if (param.getShopId() == null) {
                    throw new RuntimeException("店铺id不能为空");
                }
            }
            case MEMBER -> {
                if (param.getMemberId() == null) {
                    throw new RuntimeException("用户id不能为空");
                }
                param.setDeleteStatus(0);
            }
        }

        IPage<DbOmsOrder> ords = dbOmsOrderService.lambdaQuery()
                .eq(param.getShopId() != null, DbOmsOrder::getShopId, param.getShopId())                      // 店铺id
                .eq(param.getMemberId() != null, DbOmsOrder::getMemberId, param.getMemberId())                // 用户
                .eq(param.getStatus() != null, DbOmsOrder::getStatus, param.getStatus())                      // 状态
                .eq(param.getDeleteStatus() != null, DbOmsOrder::getDeleteStatus, param.getDeleteStatus())    // 删除状态
                .page(param.getPage().iPage());


        return ords.convert(e -> {
            OrderPageVo r = new OrderPageVo();

            List<DbOmsOrderItem> orderItems = dbOmsOrderItemService.lambdaQuery()
                    .eq(DbOmsOrderItem::getOrderId, e.getId())
                    .list();

            BeanUtils.copyProperties(e, r);
            r.setOrderItems(orderItems);

            return r;
        });
    }

    @Override
    public DbOmsOrder payOrder(Long orderId) {
        DbOmsOrder one = dbOmsOrderService.notNullOne(orderId);

        if (one.getDeleteStatus() == 1) {
            throw new RuntimeException("订单已被删除");
        }
        if (one.getStatus() != 0) {
            throw new RuntimeException("订单非待支付状态");
        }

        one.setStatus(1);

        dbOmsOrderService.updateById(one);

        return one;
    }
}
