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
 * @TableName db_shop_user_relation
 */
@TableName(value ="db_shop_user_relation")
@Data
public class DbShopUserRelation implements Serializable {
    private Long id;

    private Long userId;

    private Long shopId;

    /**
     * 用户与店铺的关系:
     * <ul style="list-style-type: none;">
     *     <li>0: 店长</li>
     *     <li>1: 客服</li>
     *     <li>2: 写手</li>
     * </ul>
     */
    private Integer relation;

    /**
     * 客服佣金比率
     * 该字段只对客服有意义
     */
    private BigDecimal csCommissionRate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long createBy;

    @Serial
    private static final long serialVersionUID = 1L;
}