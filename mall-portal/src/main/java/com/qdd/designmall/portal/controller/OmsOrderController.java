package com.qdd.designmall.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.malloms.po.OrderPageParam;
import com.qdd.designmall.malloms.service.OrderService;
import com.qdd.designmall.mallpms.service.ProductService;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import com.qdd.designmall.portal.po.OmsOrderCreateParam;
import com.qdd.designmall.portal.po.OmsOrderPageParam;
import com.qdd.designmall.portal.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Tag(name = "订单")
@RequiredArgsConstructor
@Transactional
public class OmsOrderController {
    private final OrderService orderService;
    private final DbPmsProductService dbPmsProductService;
    private final UmsMemberService umsMemberService;
    private final ProductService productService;

    @Operation(summary = "订单创建", description = "用户看中商品，下订单，但未支付")
    @PostMapping("/create")
    ResponseEntity<String > create(@RequestBody OmsOrderCreateParam param) {
        PmsProduct product = dbPmsProductService.notNullOne(param.getProductId());
        Long userId = umsMemberService.currentUserId();

        orderService.create(product,userId);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "订单分页", description = "用户查看订单列表")
    @PostMapping("/page")
    ResponseEntity<IPage<DbOmsOrder>> pageOrder(@RequestBody OmsOrderPageParam param){
        OrderPageParam orderPageParam = new OrderPageParam();
        BeanUtils.copyProperties(param,orderPageParam);

        orderPageParam.setMemberId(umsMemberService.currentUserId());
        orderPageParam.setDeleteStatus(0);

        IPage<DbOmsOrder> result = orderService.pageOrder(orderPageParam);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "订单支付", description = "用户支付订单")
    @GetMapping("/pay")
    ResponseEntity<String> payOrder(@RequestParam Long orderId) {
        DbOmsOrder order = orderService.payOrder(orderId);
        productService.sale(order.getProductId(),order.getProductQuantity());
        return ResponseEntity.ok("success");
    }
}
