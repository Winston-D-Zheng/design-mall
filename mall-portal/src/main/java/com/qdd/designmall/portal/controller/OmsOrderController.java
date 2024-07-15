package com.qdd.designmall.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.malloms.po.OrderPageParam;
import com.qdd.designmall.malloms.service.OrderService;
import com.qdd.designmall.malloms.vo.OrderPageVo;
import com.qdd.designmall.mallpms.service.ProductService;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.DbOmsOrderItem;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.service.DbOmsOrderItemService;
import com.qdd.designmall.mbp.service.DbOmsOrderService;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import com.qdd.designmall.portal.po.OmsOrderCreateParam;
import com.qdd.designmall.portal.po.OmsOrderPageParam;
import com.qdd.designmall.portal.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

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
    private final DbOmsOrderItemService dbOmsOrderItemService;
    private final DbOmsOrderService dbOmsOrderService;

    @Operation(summary = "订单创建", description = "用户看中商品，下订单，但未支付")
    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody OmsOrderCreateParam param) {
        PmsProduct product = dbPmsProductService.notNullOne(param.getProductId());
        Long userId = umsMemberService.currentUserId();

        DbOmsOrder order = orderService.orderAdd(product, userId);

        // 创建子订单项，默认价格为订单的1/10
        orderService.orderItemAdd(order.getId(), 1, order.getProductPrice().multiply(BigDecimal.valueOf(0.2)));

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "订单分页", description = "用户查看订单列表")
    @PostMapping("/page")
    ResponseEntity<IPage<OrderPageVo>> pageOrder(@RequestBody OmsOrderPageParam param) {
        OrderPageParam orderPageParam = new OrderPageParam();
        ZBeanUtils.copyProperties(param, orderPageParam);

        orderPageParam.setMemberId(umsMemberService.currentUserId());
        orderPageParam.setDeleteStatus(0);

        IPage<OrderPageVo> result = orderService.pageOrder(orderPageParam, EUserType.MEMBER);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "订单项支付", description = "用户支付订单")
    @GetMapping("/pay")
    ResponseEntity<String> payOrder(@RequestParam Long orderItemId) {
        Optional<Long> productId = orderService.orderItemPay(orderItemId);
        productId.ifPresentOrElse(
                aLong -> productService.sale(aLong, 1), // 最后一阶段完成订单,商品-1
                () -> {                                         // 否则创建下一阶段订单项
                    var orderId = dbOmsOrderItemService.notNullOrderId(orderItemId);
                    var order = dbOmsOrderService.notNullOne(orderId);
                    orderService.orderItemAdd(orderId, 1, order.getProductPrice().multiply(BigDecimal.valueOf(0.2)));
                });
        return ResponseEntity.ok("success");
    }
}
