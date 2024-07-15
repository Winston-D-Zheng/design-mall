package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @TableName db_oms_order_item
 */
@TableName(value ="db_oms_order_item")
@Data
public class DbOmsOrderItem implements Serializable {
    private Long id;

    private Long orderId;

    /**
     * 从1开始
     */
    @Schema(description = "订单阶段，从1开始")
    @Min(value = 1, message = "阶段不能小于1")
    private Integer stage;

    private BigDecimal price;

    /**
     * 支付类型。1->微信;2->支付宝
     */
    private Integer payType;

    /**
     * 订单状态：0->待付款；1->已支付；2->已完成；
     */
    @Schema(description = "订单状态：0->待付款；1->已支付；2->已完成；")
    private Integer status;

    private LocalDateTime createTime;
}