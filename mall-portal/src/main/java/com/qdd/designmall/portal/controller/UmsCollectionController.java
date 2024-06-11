package com.qdd.designmall.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.UmsProductCollection;
import com.qdd.designmall.portal.po.UmsProductCollectionPageParam;
import com.qdd.designmall.portal.service.UmsCollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户收藏管理")
@RestController
@RequestMapping("/productCollection")
@RequiredArgsConstructor
public class UmsCollectionController {
    private final UmsCollectionService umsCollectionService;

    @PostMapping("/page")
    @Operation(summary = "显示收藏")
    public ResponseEntity<IPage<UmsProductCollection>> page(@RequestBody UmsProductCollectionPageParam param) {
        var result = umsCollectionService.page(param);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/add")
    @Operation(summary = "添加收藏")
    public ResponseEntity<Void> add(@RequestParam  Long productId) {
        umsCollectionService.add(productId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/delete")
    @Operation(summary = "删除收藏")
    public ResponseEntity<Void> delete(@RequestParam  Long collectionId) {
        umsCollectionService.delete(collectionId);
        return ResponseEntity.ok(null);
    }
}
