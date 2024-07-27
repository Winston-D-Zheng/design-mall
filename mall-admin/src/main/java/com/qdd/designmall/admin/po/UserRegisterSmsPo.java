package com.qdd.designmall.admin.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
public class UserRegisterSmsPo {
    @NotBlank
    String phone;
    @Schema(description = "注册身份类型：0-员工，1-商家", requiredMode = REQUIRED)
    @Min(0)
    @Max(1)
    int type;
}
