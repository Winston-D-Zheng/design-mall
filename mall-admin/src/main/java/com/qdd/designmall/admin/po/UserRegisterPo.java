package com.qdd.designmall.admin.po;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterPo {
    @NotEmpty
    @Parameter(name = "用户名", required = true)
    private String username;

    @NotEmpty
    @Parameter(name = "密码", required = true)
    private String password;

    @Parameter(name = "用户头像")
    private String icon;

    @Email
    @Parameter(name = "邮箱")
    private String email;

    @Parameter(name = "用户昵称")
    private String nickName;

    @Parameter(name = "备注")
    private String note;
}
