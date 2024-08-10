package com.qdd.designmall.admin.controller;

import com.qdd.designmall.admin.po.UserAddRolePo;
import com.qdd.designmall.admin.po.UserRegisterSmsPo;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.admin.po.UserRegisterPo;
import com.qdd.designmall.common.enums.EAdminRole;
import com.qdd.designmall.mallexternal.service.SmsService;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.security.po.AdminLoginParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户管理")
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UmsAdminService umsAdminService;
    private final SmsService smsService;

    @Operation(summary = "获取验证码")
    @PostMapping("/sendSmsCode")
    public ResponseEntity<String> sendSmsCode(@RequestBody UserRegisterSmsPo param) {
        var phone = param.getPhone();
        var type = param.getType();

        // 验证用户表中是否被注册
        umsAdminService.duplicateThrow(phone, type);

        // 发送验证码
        smsService.sendRegisterCode(phone, type);
        return ResponseEntity.ok("success");
    }


    @Operation(summary = "注册")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterPo param) {
        var phone = param.getPhone();
        var code = param.getSmsCode();
        var password = param.getPassword();
        var type = param.getType();

        // 验证验证码可用性
        smsService.validateRegisterCode(phone, code, type);

        // 添加用户
        umsAdminService.addUser(phone, password, type);

        return ResponseEntity.ok("success");
    }


    @PostMapping("/login")
    @Operation(summary = "登陆")
    public ResponseEntity<?> login(@Validated @RequestBody AdminLoginParam param) {
        param.setIdentifier(param.getIdentifier().strip());     // 去掉首尾空格
        String token = umsAdminService.login(param);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/addRole")
    @Operation(summary = "添加角色")
    public ResponseEntity<String> addRole(@RequestBody UserAddRolePo param) {
        String role = param.getRole();
        String code = param.getCode();
        switch (EAdminRole.valueOf(role)) {
            case WRITER -> {
                umsAdminService.addWriterRole(code);
                return ResponseEntity.ok("success");
            }
            case MERCHANT -> {
                umsAdminService.addMerchantRole(code);
                return ResponseEntity.ok("success");
            }
            case CUSTOMER_SERVICE -> {
                umsAdminService.addCustomerServiceRole(code);
                return ResponseEntity.ok("success");
            }
            default -> {
                return ResponseEntity.ok("fail");
            }
        }
    }


    @GetMapping("/userInfo")
    @Operation(summary = "获取当前用户信息")
    public ResponseEntity<UmsAdmin> getUserInfo() {
        UmsAdmin umsAdmin = umsAdminService.userInfo();
        return ResponseEntity.ok(umsAdmin);
    }
}
