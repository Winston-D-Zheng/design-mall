package com.qdd.designmall.admin.controller.taobao;

import com.qdd.designmall.taobaoorder.po.ValidateConditionPo;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderService;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderUploadService;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/taobao/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final TaobaoOrderService taobaoOrderService;
    private final TaobaoOrderUploadService taobaoOrderUploadService;
    private final TaobaoOrderValidateService taobaoOrderValidateService;

    // 上传订单
    @PostMapping("/taobaoOrder/upload/shopId/{shopId}")
    ResponseEntity<String> uploadTaobaoOrder(@PathVariable Long shopId, @RequestParam("file") MultipartFile file) {
        taobaoOrderUploadService.upload(file, shopId);
        return ResponseEntity.ok("上传成功");
    }

    //TODO 订单校验
    @PostMapping("/taobaoOrder/validate/shopId/{shopId}")
    ResponseEntity<String> validateTaobaoOrder(@PathVariable Long shopId,@RequestBody ValidateConditionPo param) {
        taobaoOrderValidateService.validate(param,shopId);
        return null;
    }

    //TODO 订单修改

    //TODO 可结算订单下载
}
