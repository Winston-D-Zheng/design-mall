package com.qdd.designmall.admin.controller.tboms;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.DbTbomsWriterOrderService;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.tboms.po.WriterPageWriterOrderPo;
import com.qdd.designmall.tboms.service.OrderService;
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
    private final SecurityUserService securityUserService;

    //TODO 分页自己相关的订单
    ResponseEntity<?> pageWriterOrder(@RequestBody WriterPageWriterOrderPo param) {
        Long userId = securityUserService.currentUserDetails().getUserId();
        IPage<DbTbomsWriterOrder> rt = dbTbomsWriterOrderService.lambdaQuery()
                .eq(DbTbomsWriterOrder::getWriterId, userId)
                .page(param.getPage().iPage());

        return ResponseEntity.ok(rt);
    }


    //TODO 申请结算
}
