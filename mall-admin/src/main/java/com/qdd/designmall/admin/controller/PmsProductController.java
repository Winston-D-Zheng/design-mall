package com.qdd.designmall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.mallpms.po.ProductAddParam;
import com.qdd.designmall.mallpms.po.ProductDeleteParam;
import com.qdd.designmall.mallpms.po.ProductPageParam;
import com.qdd.designmall.mallpms.po.ProductUpdateParam;
import com.qdd.designmall.mallpms.service.ProductService;
import com.qdd.designmall.mbp.model.PmsProduct;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "商品管理", description = "PmsProductController")
@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class PmsProductController {
    private final ProductService productService;
    private final UmsAdminService umsAdminService;

    @Operation(summary = "创建商品")
    @PostMapping("/create")
//    @PreAuthorize("hasAnyAuthority('pms:produce:create')")
    public ResponseEntity<Long> create(@RequestBody ProductAddParam param) {
        Long id = productService.create(param, userId());
        return ResponseEntity.ok(id);
    }


    @Operation(summary = "更新商品")
    @PostMapping("/update")
//    @PreAuthorize("hasAnyAuthority('pms:produce:update')")
    public ResponseEntity<String > update(@Validated @RequestBody ProductUpdateParam param) {
        productService.update(param, userId());
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "获取店铺所有商品")
    @PostMapping("/page")
    public ResponseEntity<IPage<PmsProduct>> page(@RequestBody ProductPageParam param) {
        return ResponseEntity.ok(productService.page(param, userId()));
    }

    @Operation(summary = "删除商品")
    @PostMapping("/delete")
    public ResponseEntity<String > delete(@RequestBody ProductDeleteParam param) {
        productService.delete(param, userId());
        return ResponseEntity.ok("success");
    }

    private Long userId() {
        return umsAdminService.currentUserId();
    }
}
