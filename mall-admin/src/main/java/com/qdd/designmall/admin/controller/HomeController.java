package com.qdd.designmall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.*;
import com.qdd.designmall.mallhome.service.PromotionProductService;
import com.qdd.designmall.mallhome.service.PromotionService;
import com.qdd.designmall.mallhome.service.PromotionShopService;
import com.qdd.designmall.mbp.model.DbHmsPromotion;
import com.qdd.designmall.mbp.model.DbHmsPromotionProduct;
import com.qdd.designmall.mbp.model.DbHmsPromotionShop;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "首页管理")
@RestController
@RequestMapping("/admin/home")
@RequiredArgsConstructor
@Validated
public class HomeController {
    private final PromotionService promotionService;
    private final PromotionShopService promotionShopService;
    private final PromotionProductService promotionProductService;

    @PostMapping("/promotion/add")
    @Operation(summary = "推广添加")
    ResponseEntity<?> addPromotion(@RequestBody PromotionAddPo param) {
        promotionService.add(param);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/promotion/page")
    @Operation(summary = "推广分页")
    ResponseEntity<?> pagePromotion(@RequestBody PromotionPagePo param) {
        IPage<DbHmsPromotion> r = promotionService.page(param);
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/promotion/{id}")
    @Operation(summary = "推广删除")
    ResponseEntity<?> delPromotion(@PathVariable Long id) {
        promotionService.delete(id);
        return ResponseEntity.ok("success");
    }



    //*************** 商品 ***************
    @PostMapping("/promotion/product/add")
    @Operation(summary = "推荐商品添加")
    ResponseEntity<?> addPromotionProduct(@RequestBody PromotionProductAddPo param) {
        promotionProductService.add(param);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/promotion/product/{id}")
    @Operation(summary = "推荐商品删除")
    ResponseEntity<?> delPromotionProduct(@PathVariable Long id){
        promotionProductService.delete(id);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/promotion/product/page")
    ResponseEntity<?> pagePromotionProduct(@RequestBody PromotionProductPagePo param){
        IPage<DbHmsPromotionProduct> r = promotionProductService.page(param);
        return ResponseEntity.ok(r);
    }

    //*************** 店铺 ***************

    @PostMapping("/promotion/shop/add")
    @Operation(summary = "推荐店铺添加")
    ResponseEntity<?> addPromotionShop(@RequestBody PromotionShopAddPo param) {
        promotionShopService.add(param);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/promotion/shop/page")
    @Operation(summary = "分页推荐店铺")
    ResponseEntity<?> pagePromotionShop(@RequestBody PromotionShopPagePo param) {
        IPage<DbHmsPromotionShop> r = promotionShopService.page(param);
        return ResponseEntity.ok(r);
    }


    @DeleteMapping("/promotion/shop/{id}")
    @Operation(summary = "推荐店铺删除")
    ResponseEntity<?> delPromotionShop(@PathVariable Long id){
        promotionShopService.delete(id);
        return ResponseEntity.ok("success");
    }
}
