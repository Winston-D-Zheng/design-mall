package com.qdd.designmall.malloms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.malloms.po.OrderPageParam;
import com.qdd.designmall.malloms.service.OrderService;
import com.qdd.designmall.malloms.util.OrderSnGenUtil;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.service.DbOmsOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final DbOmsOrderService dbOmsOrderService;

    @Override
    public void create(PmsProduct product, Long memberId) {
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
    }

    @Override
    public IPage<DbOmsOrder> pageOrder(OrderPageParam param) {

        return dbOmsOrderService.lambdaQuery()
                .eq(param.getShopId() != null, DbOmsOrder::getShopId, param.getShopId())                      // 店铺id
                .eq(param.getMemberId() != null, DbOmsOrder::getMemberId, param.getMemberId())                // 用户
                .eq(param.getStatus() != null, DbOmsOrder::getStatus, param.getStatus())                      // 状态
                .eq(param.getDeleteStatus() != null, DbOmsOrder::getDeleteStatus, param.getDeleteStatus())    // 删除状态
                .page(param.getPageParam().iPage());
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


    /**
     * 生成18位订单编号:8位日期+2位平台号码+2位支付方式+6位以上自增id
     *
     * @param createTime 订单创建时间
     * @param platformId 2位平台号码
     * @param payType    2位支付方式
     * @return 订单sn
     */
    private String generateOrderSn(LocalDateTime createTime, Integer platformId, Integer payType) {
        String format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(createTime);
        return format + platformId + payType;
    }


}
