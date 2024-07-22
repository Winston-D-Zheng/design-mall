package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @TableName db_taobao_order
 */
@TableName(value ="db_tboms_taobao_order")
@Data
public class DbTbomsTaobaoOrder implements Serializable {
    private Long id;

    /**
     * 系统店铺id
     */
    private Long shopId;

    /**
     * 淘宝店铺名
     */
    private String taobaoShopName;

    /**
     * 订单编号
     */
    private String taobaoOrderNo;

    /**
     * 订单付款时间
     */
    private LocalDateTime taobaoPayTime;

    /**
     * 买家实际支付金额
     */
    private BigDecimal taobaoBuyerActualPaymentAmount;

    /**
     * 退款时间
     */
    private LocalDateTime taobaoRefundTime;

    /**
     * 退款金额
     */
    private BigDecimal taobaoRefundAmount;

    /**
     * 订单状态
     */
    private String taobaoOrderStatus;

    /**
     * 商家备注
     */
    private String taobaoMerchantNotes;

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