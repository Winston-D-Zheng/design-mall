package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName db_tboms_integrated_order_validation_history
 */
@TableName(value ="db_tboms_integrated_order_validation_history")
@Data
public class DbTbomsIntegratedOrderValidationHistory implements Serializable {
    private Long id;

    private Long integratedOrderId;

    private Integer orderValidatedState;

    private Integer taobaoOrderState;

    private Integer hasCorrespondingTaobaoOrderState;

    private Integer priceAmountRightState;

    private Integer payAmountRightState;

    private LocalDateTime validationAt;

    @Serial
    private static final long serialVersionUID = 1L;
}