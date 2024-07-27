package com.qdd.designmall.admin.controller.tboms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.DbTbomsWriterOrderService;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.WriterPageWriterOrderPo;
import com.qdd.designmall.tboms.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "淘宝订单-写手")
@RestController
@RequestMapping("/taobao/writer")
@RequiredArgsConstructor
public class WriterController {
    private final DbTbomsWriterOrderService dbTbomsWriterOrderService;
    private final OrderService orderService;
    private final SecurityUserService securityUserService;


    @Operation(summary = "分页写手订单")
    @PostMapping("/pageWriterOrder")
    ResponseEntity<?> pageWriterOrder(@RequestBody WriterPageWriterOrderPo param) {
        Long userId = getUserId();
        IPage<DbTbomsWriterOrder> rt = dbTbomsWriterOrderService.lambdaQuery()
                .eq(DbTbomsWriterOrder::getWriterId, userId)
                .page(param.getPage().iPage());

        return ResponseEntity.ok(rt);
    }



    @Operation(summary = "申请结算")
    @GetMapping("/approval")
    ResponseEntity<?> approval(@RequestParam Long writerOrderId) {
        // 修改状态
        Long userId = getUserId();
        DbTbomsWriterOrder writerOrder = dbTbomsWriterOrderService.lambdaQuery()
                .eq(DbTbomsWriterOrder::getId, writerOrderId)
                .eq(DbTbomsWriterOrder::getWriterId, userId)
                .eq(DbTbomsWriterOrder::getOrderState, 0)
                .one();

        if (writerOrder == null) {
            throw new RuntimeException("该订单不存在");
        }

        // 同步综合订单和写手订单状态
        Long igOrderId = writerOrder.getIntegratedOrderId();
        orderService.updateIgOrderState(igOrderId, 1);

        return ResponseEntity.ok("success");
    }

    private Long getUserId() {
        return securityUserService.currentUserDetails().getUserId();
    }
}
