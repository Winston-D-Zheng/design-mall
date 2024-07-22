package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @TableName db_tboms_integrated_order
 */
@TableName(value = "db_tboms_integrated_order")
@Data
public class DbTbomsIntegratedOrder implements Serializable {
    private Long id;

    private Long shopId;

    private String taobaoOrderNo;

    /**
     * 验证通过状态。
     * 0: 未验证
     * -1: 未通过验证
     * 1: 通过验证
     * <p>
     * 通过验证需要：
     * 订单金额正确
     * 利润率正确
     * 订单状态正确（交易成功）
     */
    private Integer orderValidatedState = 0;

    /**
     * 淘宝订单状态
     * 0: 未成功
     * 1: 交易成功
     */
    private Integer taobaoOrderState = 0;

    /**
     * 录入人id
     */
    private Long updaterId;

    /**
     * 对应的客服订单是否都有对应的淘宝订单
     * 0: 否
     * 1: 是
     */
    private Integer hasCorrespondingTaobaoOrderState = 0;

    private BigDecimal orderPriceAmount;

    private BigDecimal taobaoOrderPriceAmount;

    /**
     * 对应的客服订单是否与淘宝订单价格都是正确的
     * 0: 否
     * 1: 是
     */
    private Integer priceAmountRightState = 0;

    /**
     * 利润率
     */
    private BigDecimal profileMargin;

    /**
     * 应付金额
     */
    private BigDecimal shouldPayAmount;

    /**
     * 支付金额是否正确
     * 0: 否
     * 1: 是
     */
    private Integer payAmountRightState = 0;

    /**
     * 锁定状态，无法更新到数据库
     * 0: 未锁定
     * 1: 锁定
     * 使用场景：
     * 订单支付后，锁定状态
     */
    private Integer lock = 0;

    /**
     * 校验版本号
     */
    String validationVersion;

    @Serial
    private static final long serialVersionUID = 1L;
}