package com.qdd.designmall.admin.controller;

import com.qdd.designmall.admin.po.UserRegisterPo;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.security.po.UserLoginParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "后台用户管理")
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {
    private final UmsAdminService umsAdminService;

    @PostMapping("/register")
    @Operation(summary = "注册")
    public ResponseEntity<String> register(@Validated @RequestBody UserRegisterPo param) {
       umsAdminService.register(param);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/login")
    @Operation(summary = "登陆")
    public ResponseEntity<?> login(@Validated @RequestBody UserLoginParam param) {
        String  token = umsAdminService.login(param);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/userInfo")
    @Operation(summary = "获取当前用户信息")
    public ResponseEntity<UmsAdmin> getUserInfo() {
        UmsAdmin umsAdmin = umsAdminService.userInfo();
        return ResponseEntity.ok(umsAdmin);
    }
}
