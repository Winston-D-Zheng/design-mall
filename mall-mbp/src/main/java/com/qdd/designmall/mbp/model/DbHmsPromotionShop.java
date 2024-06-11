package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

/**
 * @TableName db_hms_promotion_shop
 */
@TableName(value ="db_hms_promotion_shop")
@Data
public class DbHmsPromotionShop implements Serializable {
    private Long id;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String pic;

    private String name;

    private Date startDate;

    private Date endDate;

    private Long shopId;

    /**
     * 0-> 隐藏；1-> 显示
     */
    private Integer status;
}