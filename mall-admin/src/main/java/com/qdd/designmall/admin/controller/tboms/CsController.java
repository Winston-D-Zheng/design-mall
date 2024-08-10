package com.qdd.designmall.admin.controller.tboms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.service.PermissionService;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.DbShopUserRelationService;
import com.qdd.designmall.mbp.service.DbTbomsCustomerServiceOrderService;
import com.qdd.designmall.mbp.service.DbTbomsIntegratedOrderService;
import com.qdd.designmall.mbp.service.DbUmsAdminService;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.*;
import com.qdd.designmall.tboms.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final DbUmsAdminService dbUmsAdminService;
    private final DbTbomsCustomerServiceOrderService dbTbomsCustomerServiceOrderService;
    private final PermissionService permissionService;


    @Operation(summary = "获取写手id",description = "根据 电话/用户名/uuid 获取写手id，可判断写手是否已注册，是否属于该店铺")
    @GetMapping("/getWriterId")
    @PreAuthorize("@permissionService.isMerchantOrCs(#shopId)")
    ResponseEntity<?> getWriterId(@RequestParam Long shopId, @RequestParam String identifier) {
        // 验证当前用户是该店铺的客服或店长
//        Long currentUserId = securityUserService.currentUserDetails().getUserId();
//        boolean merchantOrCs = dbShopUserRelationService.isMerchantOrCs(shopId, currentUserId);
//        if (!merchantOrCs) {
//            throw new RuntimeException("当前用户无权查看店铺内容");
//        }

        // 获取写手id
        Long writerId = dbUmsAdminService.getUserId(identifier, 0);
        if (writerId == null) {
            throw new RuntimeException("写手不存在");
        }

        // 判断写手是否属于该店铺
        boolean exists = dbShopUserRelationService.isWriter(shopId, writerId);
        if (!exists) {
            throw new RuntimeException("写手不属于该店铺");
        }

        return ResponseEntity.ok(writerId);
    }


    @Operation(summary = "验证淘宝订单是否可用")
    @GetMapping("/validateTaobaoOrderNo")
    ResponseEntity<?> validateTaobaoOrderNo(@RequestParam Long shopId,@RequestParam String taobaoOrderNo){
        boolean taobaoInUse = dbTbomsCustomerServiceOrderService.isTaobaoInUse(shopId, taobaoOrderNo);
        if (taobaoInUse) {
            throw new RuntimeException("淘宝订单已被使用");
        }
        return ResponseEntity.ok("success");
    }


    @Operation(summary = "获取客服佣金比率")
    @GetMapping("/csCommissionRate")
    @PreAuthorize("@permissionService.isMerchantOrCs(#shopId)")
    ResponseEntity<?> getCsCommissionRate(@RequestParam Long shopId) {
        Long userId = securityUserService.currentUserDetails().getUserId();
        return ResponseEntity.ok(dbShopUserRelationService.getCsCommissionRate(shopId, userId));
    }

    @Operation(summary = "输入订单")
    @PostMapping("/inputOrder")
    @PreAuthorize("@permissionService.ableToModifyOrder(#param.shopId)")
    ResponseEntity<String> inputOrder(@RequestBody CsOrderInputPo param) {
        orderService.input(param);
        return ResponseEntity.ok("success");
    }


    @Operation(summary = "审批订单")
    @PostMapping("/approval")
    ResponseEntity<String> approval(@RequestBody ApprovalOrderPo param) {

        Long igOrderId = param.getIgOrderId();
        int orderState = param.isPass() ? 3 : 2;
        DbTbomsIntegratedOrder igOrder = dbTbomsIntegratedOrderService.lambdaQuery()
                .eq(DbTbomsIntegratedOrder::getId, igOrderId)
                .eq(DbTbomsIntegratedOrder::getOrderState, 1)
                .one();
        if (igOrder == null) {
            throw new RuntimeException("综合订单不存在");
        }
        if (!permissionService.ableToModifyOrder(igOrder.getShopId())){
            throw new RuntimeException("当前用户无权修改订单");
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
