package com.qdd.designmall.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.CmsProductComment;
import com.qdd.designmall.portal.po.CmsPortalProductCommentPageParam;
import com.qdd.designmall.portal.po.CmsProductCommentCreateParam;
import com.qdd.designmall.portal.service.CmsCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户评论管理")
@RestController
@RequestMapping("/productComment")
@RequiredArgsConstructor
@Validated
public class CmsCommentController {
    private final CmsCommentService cmsCommentService;

    @Operation(summary = "创建评论")
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CmsProductCommentCreateParam param) {
        cmsCommentService.create(param);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "删除评论")
    @GetMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Long commentId) {
        cmsCommentService.delete(commentId);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "显示评论")
    @PostMapping("/page")
    public ResponseEntity<IPage<CmsProductComment>> page(@RequestBody CmsPortalProductCommentPageParam param) {
        return ResponseEntity.ok(cmsCommentService.page(param));
    }
}
