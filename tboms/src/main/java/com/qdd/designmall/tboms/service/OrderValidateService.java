package com.qdd.designmall.tboms.service;

import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.tboms.po.OrderValidateConditionPo;

import java.util.Map;

public interface OrderValidateService {
    /**
     * 校验结果
     *
     * @param param 校验条件
     * @return {
     * "综合订单id"：{
     * "综合订单"：{@link DbTbomsIntegratedOrder},"客服订单"：[{@link DbTbomsCustomerServiceOrder}],"写手订单“:[{@link DbTbomsWriterOrder}]}}
     */
    Map<Long, Map<String, Object>> validate(OrderValidateConditionPo param);
}
