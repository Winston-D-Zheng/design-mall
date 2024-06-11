package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

/**
 * @TableName pms_promotion
 */
@TableName(value ="db_hms_promotion")
@Data
public class DbHmsPromotion implements Serializable {
    private Long id;

    private String title;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String pic;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long productId;

    /**
     * 0-> 隐藏；1-> 显示
     */
    private Integer status;
}