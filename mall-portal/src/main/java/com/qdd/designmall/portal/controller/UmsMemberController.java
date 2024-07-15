package com.qdd.designmall.portal.controller;

import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.portal.po.MemberUpdatePo;
import com.qdd.designmall.portal.po.UmsRegisterParam;
import com.qdd.designmall.portal.service.UmsMemberService;
import com.qdd.designmall.security.po.UserLoginParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "会员")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class UmsMemberController {
    private final UmsMemberService umsMemberService;

    @Operation(summary = "会员注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody UmsRegisterParam param) {
        umsMemberService.register(param);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "会员登陆")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginParam param) {
        String  token = umsMemberService.login(param);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "获取会员信息")
    @GetMapping(value = "/info")
    public ResponseEntity<UmsMember> info() {
        UmsMember member = umsMemberService.userInfo();
        return ResponseEntity.ok(member);
    }

    @Operation(summary = "更新会员信息")
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody MemberUpdatePo param){
        umsMemberService.updateInfo(param);
        return ResponseEntity.ok("success");
    }
}
