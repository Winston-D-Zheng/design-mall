package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;

@TableName(value ="ums_admin")
@Data
public class UmsAdmin implements Serializable {

    @Serial
    private static final long serialVersionUID = 3417943140717202344L;

    private Long id;

    private String username;

    private String password;

    private String icon;

    private String email;

    private String nickName;

    private String note;

    private LocalDate createTime;

    private LocalDate loginTime;

    private Integer status;
}