package com.qdd.designmall.portal.controller;

import com.qdd.designmall.mallpms.po.ProductSearchParam;
import com.qdd.designmall.mallpms.service.CategoryService;
import com.qdd.designmall.mallpms.service.ProductService;
import com.qdd.designmall.mallpms.vo.CategoryAllVo;
import com.qdd.designmall.mallpms.vo.ProductDetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品页")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class PmsProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Operation(summary = "综合搜索、筛选、排序")
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody ProductSearchParam searchParam) {
        var r = productService.search(searchParam);

        return ResponseEntity.ok(r);
    }

    @Operation(summary = "广告位产品")
    @GetMapping("/advertiseProduct")
    public void listAdvertiseProduct() {
    }


    @Operation(summary = "商品详情")
    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam Long productId) {
        ProductDetailVo result = productService.detail(productId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "分类获取")
    @GetMapping("/category")
    public ResponseEntity<?> allCategory() {
        List<CategoryAllVo> r = categoryService.getAll();
        return ResponseEntity.ok(r);
    }
}
