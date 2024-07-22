package com.qdd.designmall.admin.controller.tboms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.tboms.po.CustomerServiceOrderInputPo;
import com.qdd.designmall.tboms.po.PageIgOrderPo;
import com.qdd.designmall.tboms.service.OrderService;
import com.qdd.designmall.tboms.po.PageCsOrderPo;
import com.qdd.designmall.tboms.po.PageWriterOrderPo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "淘宝订单-客服")
@RestController
@RequestMapping("/taobao/customerService")
@RequiredArgsConstructor
public class CsController {
    private final OrderService orderService;


    @Operation(summary = "输入订单，写手信息")
    @PostMapping("/inputOrder")
    ResponseEntity<String> inputOrder(@RequestBody CustomerServiceOrderInputPo param) {
        orderService.input(param);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "分页综合订单")
    @PostMapping("/pageIgOrder")
    ResponseEntity<?> pageIntegratedOrder(@RequestBody PageIgOrderPo param) {
        IPage<DbTbomsIntegratedOrder> rt = orderService.pageIntegratedOrder(param, 1);

        return ResponseEntity.ok(rt);
    }

    @Operation(summary = "分页客服订单")
    @PostMapping("/pageCsOrder")
    ResponseEntity<?> pageCsOrder(@RequestBody PageCsOrderPo param) {
        IPage<DbTbomsCustomerServiceOrder> rt = orderService.pageCsOrder(param, 1);

        return ResponseEntity.ok(rt);
    }

    @Operation(summary = "分页写手订单")
    @PostMapping("/pageWriterOrder")
    ResponseEntity<?> pageWriterOrder(@RequestBody PageWriterOrderPo param) {
        IPage<DbTbomsWriterOrder> rt = orderService.pageWriterOrder(param, 1);

        return ResponseEntity.ok(rt);
    }
}
