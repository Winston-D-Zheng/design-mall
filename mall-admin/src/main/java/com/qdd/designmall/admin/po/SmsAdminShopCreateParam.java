package com.qdd.designmall.admin.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qdd.designmall.common.constraint.ZPattern;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SmsAdminShopCreateParam {
    @NotBlank(message = "店名不能为空")
    @ApiModelProperty("店名")
    private String name;
    @NotBlank(message = "图片不能为空")
    @ApiModelProperty("头像")
    String pic;
    @NotNull
    @ApiModelProperty("店铺状态,-1注销，0关店，1开业")
    @NotNull
    @ZPattern(regexp = "[01]")
    private Integer status;
}
