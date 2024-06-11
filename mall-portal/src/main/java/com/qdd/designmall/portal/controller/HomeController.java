package com.qdd.designmall.portal.controller;

import com.qdd.designmall.mallhome.po.PromotionProductAddPo;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "主页")
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final PromotionService promotionService;
    private final PromotionShopService promotionShopService;
    private final PromotionProductService promotionProductService;

    @Operation(summary = "活动区")
    @GetMapping("/activity")
    ResponseEntity<?> listActivity() {
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "商品区")
    @GetMapping("/product")
    ResponseEntity<?> listProduct() {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/promotion/show")
    @Operation(summary = "显示推广")
    ResponseEntity<?> showPromotion(){
        List<DbHmsPromotion> r = promotionService.show();
        return ResponseEntity.ok(r);
    }


    //*************** 商品 ***************
    @PostMapping("/promotion/product/show")
    @Operation(summary = "推荐商品展示")
    ResponseEntity<?> showPromotionProduct() {
        List<DbHmsPromotionProduct> r = promotionProductService.show();
        return ResponseEntity.ok(r);
    }

    //*************** 店铺 ***************
    @PostMapping("/promotion/shop/show")
    @Operation(summary = "推荐店铺展示")
    ResponseEntity<?> showPromotionShop() {
        List<DbHmsPromotionShop> r = promotionShopService.show();
        return ResponseEntity.ok(r);
    }
}
