package com.qdd.designmall.malloms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.malloms.po.OrderPageParam;
import com.qdd.designmall.malloms.vo.OrderPageVo;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.PmsProduct;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderService {

    // *************** 订单管理 *****************
    DbOmsOrder orderAdd(PmsProduct product, Long memberId);

    /**
     * 分页订单
     *
     * @param param    参数。注意：商家端请求shopId不能为null；客户端请求memberId不能为null，deleteStatus必须为0。
     * @param userType 用户类型
     * @return 订单
     */
    IPage<OrderPageVo> pageOrder(OrderPageParam param, EUserType userType);


    /**
     * 支付订单
     * @param orderId 订单id
     * @Deprecated 考虑使用orderItemPay
     */
    @Deprecated
    DbOmsOrder payOrder(Long orderId);




    // *************** 订单项管理 *****************

    /**
     * 添加订单项
     * @param orderId   订单id
     * @param stage     阶段
     * @param price     设置价格
     */
    void orderItemAdd(Long orderId, Integer stage, BigDecimal price);


    /**
     * 修改订单项金额
     * @param orderItemId id
     * @param price 价格
     */
    void orderItemChangePrice(Long orderItemId, BigDecimal price);


    /**
     * 订单项支付
     *
     * @param orderItemId 订单项id
     * @return 需要减少库存的商品id，如果为空则不需要减少库存
     */
    Optional<Long> orderItemPay(Long orderItemId);

}
