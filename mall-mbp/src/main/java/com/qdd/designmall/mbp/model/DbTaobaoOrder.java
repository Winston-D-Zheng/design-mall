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
 * @TableName db_taobao_order
 */
@TableName(value ="db_taobao_order")
@Data
public class DbTaobaoOrder implements Serializable {
    private Long id;

    /**
     * 系统店铺id
     */
    private Long shopId;

    /**
     * 淘宝店铺名
     */
    private String shopName;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 订单付款时间
     */
    private LocalDateTime payTime;

    /**
     * 买家实际支付金额
     */
    private BigDecimal buyerActualPaymentAmount;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 商家备注
     */
    private String merchantNotes;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 更新人id
     */
    private Long updaterId;

    @Serial
    private static final long serialVersionUID = 1L;
}