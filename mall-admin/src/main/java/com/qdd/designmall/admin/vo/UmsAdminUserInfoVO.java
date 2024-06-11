package com.qdd.designmall.admin.vo;

import com.qdd.designmall.mbp.model.UmsPermission;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UmsAdminUserInfoVO {
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

    private List<UmsPermission> permissions;
}
