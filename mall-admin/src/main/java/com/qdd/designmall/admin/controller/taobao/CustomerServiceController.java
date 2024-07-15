package com.qdd.designmall.admin.controller.taobao;

import com.qdd.designmall.taobaoorder.po.OrderAndWriterInputPo;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/taobao/customerService")
@RequiredArgsConstructor
public class CustomerServiceController {
    private final TaobaoOrderService taobaoOrderService;

    // 输入订单，写手信息
    @PostMapping("/inputOrder")
    ResponseEntity<String> inputOrder(@RequestBody OrderAndWriterInputPo param) {
        taobaoOrderService.input(param);
        return ResponseEntity.ok("success");
    }

    // 设置应付工资
    @PatchMapping("/shouldPay/{id}")
    ResponseEntity<String> setShouldPay(@PathVariable Long id, BigDecimal value) {
        taobaoOrderService.setShouldPay(id,value);
        return ResponseEntity.ok("success");
    }
}
