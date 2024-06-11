package com.qdd.designmall.portal.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UmsMemberParam {
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private Integer status;

    private String icon;

    private Integer gender;

    private LocalDateTime birthday;

    private String city;

    private String job;

    private Integer sourceType;
}
