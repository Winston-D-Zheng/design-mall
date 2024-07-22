package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * @TableName db_tboms_customer_service_order
 */
@TableName(value = "db_tboms_customer_service_order")
@Data
public class DbTbomsCustomerServiceOrder implements Serializable {
    private Long id;

    private Long shopId;

    private Long integratedOrderId;

    private String taobaoOrderNo;

    /**
     * 淘宝订单状态
     * 0: 交易关闭
     * 1: 买家已下单，未付款
     * 2: 买家已付款，等待卖家发货
     * 3: 卖家已发货，等待买家确认
     * 4: 交易成功
     */
    private Integer taobaoOrderState;

    private Integer hasCorrespondingTaobaoOrderState;

    private BigDecimal orderPriceAmount;

    private BigDecimal taobaoOrderPriceAmount;

    private Integer priceAmountRightState;

    private Long updaterId;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Serial
    private static final long serialVersionUID = 1L;
}