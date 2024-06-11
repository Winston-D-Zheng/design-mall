package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@TableName(value ="ums_permission")
@Data
public class UmsPermission implements Serializable {
    @Serial
    private static final long serialVersionUID = -3349282840847014151L;

    private Long id;

    private String name;

    private String value;

    private Integer status;

    private Date createTime;
}