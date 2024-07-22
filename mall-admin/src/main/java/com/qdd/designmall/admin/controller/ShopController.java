package com.qdd.designmall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.po.SmsAdminShopPagePram;
import com.qdd.designmall.admin.po.SmsAdminShopCreateParam;
import com.qdd.designmall.admin.po.SmsAdminShopUpdateParam;
import com.qdd.designmall.admin.service.SmsAdminShopService;
import com.qdd.designmall.admin.vo.ShopPageAllPo;
import com.qdd.designmall.mbp.model.SmsShop;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/shop")
@Tag(name = "店铺管理")
@RequiredArgsConstructor
public class ShopController {
    private final SmsAdminShopService smsAdminShopService;

    @PostMapping("/create")
    @Operation(summary = "创建店铺")
    @PreAuthorize("hasRole(EAdminRole.MERCHANT.name())")
    ResponseEntity<Long> create(@RequestBody SmsAdminShopCreateParam param) {
        return ResponseEntity.ok(smsAdminShopService.create(param));
    }

    @PostMapping("/page")
    @Operation(summary = "显示店铺")
    ResponseEntity<IPage<SmsShop>> page(@RequestBody SmsAdminShopPagePram param) {
        return ResponseEntity.ok(smsAdminShopService.page(param));
    }

    @PostMapping("/update")
    @Operation(summary = "更新店铺")
    ResponseEntity<Long> update(@RequestBody SmsAdminShopUpdateParam param) {
        smsAdminShopService.update(param);
        return ResponseEntity.ok(null);
    }

    // 只有系统管理员可用该api
    @PostMapping("/pageAll")
    @Operation(summary = "获取所有商店信息")
    ResponseEntity<IPage<SmsShop>> pageAll(@RequestBody ShopPageAllPo param) {
        return ResponseEntity.ok(smsAdminShopService.pageAll(param));
    }

}
