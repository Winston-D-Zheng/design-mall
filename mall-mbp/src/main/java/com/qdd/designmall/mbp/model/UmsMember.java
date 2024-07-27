package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

/**
 * @TableName ums_member
 */
@TableName(value ="ums_member")
@Data
public class UmsMember implements Serializable {
    @Serial
    private static final long serialVersionUID = 2079153922492850142L;

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private Integer status;

    private LocalDateTime createTime;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String icon;

    private Integer gender;

    private LocalDateTime birthday;

    private String city;

    private String job;

    private Integer sourceType;
}