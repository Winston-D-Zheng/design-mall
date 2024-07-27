package com.qdd.designmall.common.controller;

import com.qdd.designmall.common.service.PicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@Tag(name = "图片服务")
@RestController
@RequestMapping("/pic")
@RequiredArgsConstructor
public class PicController {
    private final PicService picService;

    @Operation(summary = "获取七牛云上传凭证")
    @GetMapping("/upToken")
    ResponseEntity<String> getUpToken() {
        String upToken = picService.getUpToken();
        return ResponseEntity.ok(upToken);
    }

    @Operation(summary = "图片下载连接")
    @GetMapping("/url")
    ResponseEntity<String> getUrl(@RequestParam String picName) {
        String url = picService.getUrl(picName);
        return ResponseEntity.ok(url);
    }

    @Operation(summary = "删除图片")
    @GetMapping("/del")
    ResponseEntity<String> del(@RequestParam List<String> picKeys) {
        picService.delPic(new HashSet<>(picKeys));
        return ResponseEntity.ok("success");
    }
}