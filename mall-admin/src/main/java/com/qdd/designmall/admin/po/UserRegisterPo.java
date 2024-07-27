package com.qdd.designmall.admin.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterPo {

    @NotBlank
    private String phone;

    @NotBlank
    private String password;

    @NotBlank
    private String smsCode;

    /**
     * @see com.qdd.designmall.mbp.model.UmsAdmin#type
     */
    @Schema(description = "注册身份类型。0-员工，1-商家")
    @Min(0)
    @Max(1)
    private int type;
}
