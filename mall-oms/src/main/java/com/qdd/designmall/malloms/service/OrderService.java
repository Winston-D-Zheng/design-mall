package com.qdd.designmall.malloms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.malloms.po.OrderPageParam;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.PmsProduct;

public interface OrderService {
    void create(PmsProduct product, Long memberId);

    /**
     * 分页订单
     * @param param 参数。注意：商家端请求shopId不能为null；客户端请求memberId不能为null，deleteStatus必须为0。
     * @return 订单
     */
    IPage<DbOmsOrder> pageOrder(OrderPageParam param);

    DbOmsOrder payOrder(Long orderId);
}
