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
 * @TableName ums_product_collection
 */
@TableName(value ="ums_product_collection")
@Data
public class UmsProductCollection implements Serializable {
    @Serial
    private static final long serialVersionUID = 3857718125265597359L;

    private Long id;

    private Long memberId;

    private Long productId;

    private Long shopId;

    private LocalDateTime createTime;

    private Integer status;
}