package com.qdd.designmall.taobaoorder.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 淘宝订单 + 写手id
 */
@Data
public class OrderAndWriterInputPo {
    /**
     * 淘宝订单id
     */
    String taobaoOrderNo;

    /**
     * 写手id
     */
    Long writerId;

    /**
     * 应付工资
     */
    BigDecimal shouldPay;
}
