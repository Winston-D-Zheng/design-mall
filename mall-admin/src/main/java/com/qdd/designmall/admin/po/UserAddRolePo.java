package com.qdd.designmall.admin.po;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class UserAddRolePo {
    @Parameter(name = "角色",description = "WRITER, CUSTOMER_SERVICE, MERCHANT")
    String role;
    String code;
}
