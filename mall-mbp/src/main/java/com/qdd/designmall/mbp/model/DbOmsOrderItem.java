package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName db_oms_order_item
 */
@TableName(value ="db_oms_order_item")
@Data
public class DbOmsOrderItem implements Serializable {
    private Long id;

    private Long orderId;

    private BigDecimal price;

    /**
     * 支付类型。1->微信;2->支付宝
     */
    private Integer payType;

    private Date createTime;
}