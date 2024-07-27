package com.qdd.designmall.admin.controller.tboms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.DbShopUserRelationService;
import com.qdd.designmall.mbp.service.DbTbomsIntegratedOrderService;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.*;
import com.qdd.designmall.tboms.service.OrderService;
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
    private final DbTbomsIntegratedOrderService dbTbomsIntegratedOrderService;
    private final SecurityUserService securityUserService;
    private final DbShopUserRelationService dbShopUserRelationService;

    @Operation(summary = "获取客服佣金比率")
    @GetMapping("/csCommissionRate")
    ResponseEntity<?> getCsCommissionRate(@RequestParam Long shopId){
        Long userId = securityUserService.currentUserDetails().getUserId();
        return ResponseEntity.ok(dbShopUserRelationService.getCsCommissionRate(shopId,userId));
    }

    @Operation(summary = "输入订单")
    @PostMapping("/inputOrder")
    ResponseEntity<String> inputOrder(@RequestBody CustomerServiceOrderInputPo param) {
        orderService.input(param);
        return ResponseEntity.ok("success");
    }


    @Operation(summary = "审批订单")
    @PostMapping("/approval")
    ResponseEntity<String> approval(@RequestBody ApprovalOrderPo param) {
        Long userId = securityUserService.currentUserDetails().getUserId();
        Long igOrderId = param.getIgOrderId();
        int orderState = param.isPass() ? 3 : 2;
        DbTbomsIntegratedOrder igOrder = dbTbomsIntegratedOrderService.lambdaQuery()
                .eq(DbTbomsIntegratedOrder::getId, igOrderId)
                .eq(DbTbomsIntegratedOrder::getOrderState, 1)
                .eq(DbTbomsIntegratedOrder::getUpdaterId, userId)   // 确保是录入人操作
                .one();
        if (igOrder == null) {
            return ResponseEntity.ok("综合订单不存在");
        }

        // 更新综合订单和相关写手订单状态
        orderService.updateIgOrderState(igOrder, orderState);
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
