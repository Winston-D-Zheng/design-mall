package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName sms_shop
 */
@TableName(value ="sms_shop")
@Data
public class SmsShop implements Serializable {
    @Serial
    private static final long serialVersionUID = 1020481059401779366L;
    private Long id;

    private String name;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String pic;
    
    private Long ownerId;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}