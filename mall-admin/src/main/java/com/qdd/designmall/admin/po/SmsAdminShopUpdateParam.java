package com.qdd.designmall.admin.po;

import com.qdd.designmall.common.constraint.ZPattern;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SmsAdminShopUpdateParam {
    @NotNull
    private Long id;
    @NotBlank
    @ApiModelProperty("店名")
    private String name;
    @NotNull
    @ApiModelProperty("店铺状态,-1注销，0关店，1开业")
    @NotNull
    @ZPattern(regexp = "[01]")
    private Integer status;
}
