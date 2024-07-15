package com.qdd.designmall.admin.controller;

import com.qdd.designmall.mallpms.po.CategoryAddParam;
import com.qdd.designmall.mallpms.po.CategoryUpdatePo;
import com.qdd.designmall.mallpms.service.CategoryService;
import com.qdd.designmall.mallpms.vo.CategoryAllVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Tag(name = "商品分类管理")
@RequiredArgsConstructor
@Validated
public class PmsCategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    @Operation(summary = "添加分类")
    ResponseEntity<?> add(@RequestBody CategoryAddParam param){
        categoryService.add(param);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @Operation(summary = "获取分类")
    ResponseEntity<?> getAll(){
        List<CategoryAllVo> r = categoryService.getAll();
        return ResponseEntity.ok(r);
    }

    @PutMapping
    @Operation(summary = "更新")
    ResponseEntity<?> update(@RequestBody CategoryUpdatePo param){
        categoryService.update(param);
        return ResponseEntity.ok("success");
    }
}
