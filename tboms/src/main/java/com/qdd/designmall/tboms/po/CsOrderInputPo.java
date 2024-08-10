package com.qdd.designmall.tboms.po;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客服订单输入参数
 */
@Data
public class CsOrderInputPo {
    Long shopId;

    List<TaobaoOrder> taobaoOrders;  // <订单编号, 订单金额，退款金额>
    List<Writer> writers;            // <写手id, 应付金额>

    @Data
    public static class TaobaoOrder {
        String orderNo;              // 订单编号
        BigDecimal orderPrice;       // 订单金额
        BigDecimal refundAmount;     // 退款金额
    }

    @Data
    public static class Writer {
        Long writerId;               // 写手id
        BigDecimal shouldPay;        // 应付金额
    }
}
