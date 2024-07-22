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
 * @TableName db_shop_user_relation
 */
@TableName(value ="db_shop_user_relation")
@Data
public class DbShopUserRelation implements Serializable {
    private Long id;

    private Long userId;

    private Long shopId;

    /**
     * 用户与店铺的关系
     * 0: 未定义
     * 1: 客服
     * 2: 写手
     */
    private Integer relation;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Long createBy;

    @Serial
    private static final long serialVersionUID = 1L;
}