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
 * @TableName db_taobao_order_and_writer
 */
@TableName(value ="db_taobao_order_and_writer")
@Data
public class DbTaobaoOrderAndWriter implements Serializable {
    private Long id;

    private String taobaoOrderNo;

    private BigDecimal taobaoOrderPrice;

    /**
     * 0：未结算
     * 1: 已结算
     */
    private Integer taobaoOrderState;

    private Long writerId;

    private BigDecimal shouldPay;

    /**
     * 0: 写手未申请
     * 1: 写手已申请
     * 2: 待支付
     * 3: 已支付
     */
    private Integer payState;

    private String paymentOrderNo;

    private Date payTime;

    private Integer isDelete;

    private Long customerServiceId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}