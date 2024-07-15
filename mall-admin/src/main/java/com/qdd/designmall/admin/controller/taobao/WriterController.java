package com.qdd.designmall.admin.controller.taobao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTaobaoOrderAndWriter;
import com.qdd.designmall.taobaoorder.po.WriterPageOrderPo;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taobao/writer")
@RequiredArgsConstructor
public class WriterController {
    private final TaobaoOrderService taobaoOrderService;

    // 分页自己相关的订单
    @GetMapping("/pageOrder")
    ResponseEntity<?> pageOrder(WriterPageOrderPo param) {
        IPage<DbTaobaoOrderAndWriter> r = taobaoOrderService.writerPageOrder(param);
        return ResponseEntity.ok(r);
    }

    // 申请结算
    @PatchMapping("/settlement/order/{id}")
    ResponseEntity<String> settlement(@PathVariable Long id) {
        taobaoOrderService.settlement(id);
        return ResponseEntity.ok("success");
    }
}
