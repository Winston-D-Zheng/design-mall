package com.qdd.designmall.admin.controller;

import com.qdd.designmall.admin.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/invitation")
@Tag(name = "邀请管理")
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;


    @Operation(summary = "生成写手邀请二维码")
    @GetMapping("/generateWriterInviteCode")
    ResponseEntity<String> generateWriterInviteCode(@RequestParam Long shopId) {
        String rt = invitationService.generateWriterInviteCode(shopId);
        return ResponseEntity.ok(rt);
    }


    @Operation(summary = "扫描写手邀请二维码")
    @GetMapping("/scanWriterInviteCode")
    ResponseEntity<String> scanWriterInviteCode(@RequestParam Long shopId, @RequestParam LocalDateTime expireTime) {
        invitationService.scanWriterInviteCode(shopId, expireTime);
        return ResponseEntity.ok("success");
    }


    @Operation(summary = "生成客服邀请二维码")
    @GetMapping("/generateCustomerServiceInviteCode")
    ResponseEntity<String> generateCustomerServiceInviteCode(@RequestParam Long shopId) {
        String rt = invitationService.generateCustomerServiceInviteCode(shopId);
        return ResponseEntity.ok(rt);
    }


    @Operation(summary = "扫描客服邀请二维码")
    @GetMapping("/scanCustomerServiceInviteCode")
    ResponseEntity<String> scanCustomerServiceInviteCode(@RequestParam Long shopId, @RequestParam LocalDateTime expireTime, @RequestParam String uniqueId) {
        invitationService.scanCustomerServiceInviteCode(shopId, expireTime, uniqueId);
        return ResponseEntity.ok("success");
    }
}
