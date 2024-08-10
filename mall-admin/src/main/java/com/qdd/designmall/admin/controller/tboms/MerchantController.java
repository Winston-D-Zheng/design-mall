package com.qdd.designmall.admin.controller.tboms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.tboms.po.OrderValidateConditionPo;
import com.qdd.designmall.tboms.po.PageIgOrderPo;
import com.qdd.designmall.tboms.service.OrderService;
import com.qdd.designmall.tboms.service.TaobaoOrderUploadService;
import com.qdd.designmall.tboms.service.OrderValidateService;
import com.qdd.designmall.tboms.po.PageCsOrderPo;
import com.qdd.designmall.tboms.po.PageWriterOrderPo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name = "淘宝订单-商家")
@RestController
@RequestMapping("/taobao/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final OrderService orderService;
    private final TaobaoOrderUploadService taobaoOrderUploadService;
    private final OrderValidateService orderValidateService;

    @Operation(summary = "上传淘宝订单")
    @PostMapping("/taobaoOrder/upload/shopId/{shopId}")
    ResponseEntity<String> uploadTaobaoOrder(@PathVariable Long shopId, @RequestParam("file") MultipartFile file) {
        taobaoOrderUploadService.upload(file, shopId);
        return ResponseEntity.ok("上传成功");
    }

    @Operation(summary = "订单校验")
    @PostMapping("/taobaoOrder/validate")
    ResponseEntity<?> validateTaobaoOrder(@RequestBody OrderValidateConditionPo param) {
        Map<Long, Map<String, Object>> r = orderValidateService.validate(param);
        return ResponseEntity.ok(r);
    }

    @Operation(summary = "分页综合订单")
    @PostMapping("/pageIgOrder")
    ResponseEntity<?> pageIntegratedOrder(@RequestBody PageIgOrderPo param) {
        IPage<DbTbomsIntegratedOrder> rt = orderService.pageIntegratedOrder(param, 0);

        return ResponseEntity.ok(rt);
    }

    @Operation(summary = "分页客服订单")
    @PostMapping("/pageCsOrder")
    ResponseEntity<?> pageCsOrder(@RequestBody PageCsOrderPo param) {
        IPage<DbTbomsCustomerServiceOrder> rt = orderService.pageCsOrder(param, 0);

        return ResponseEntity.ok(rt);
    }

    @Operation(summary = "分页写手订单")
    @PostMapping("/pageWriterOrder")
    ResponseEntity<?> pageWriterOrder(@RequestBody PageWriterOrderPo param) {
        IPage<DbTbomsWriterOrder> rt = orderService.pageWriterOrder(param, 0);

        return ResponseEntity.ok(rt);
    }
}
