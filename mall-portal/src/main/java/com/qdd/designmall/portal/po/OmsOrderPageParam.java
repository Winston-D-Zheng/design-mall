package com.qdd.designmall.portal.po;

import com.qdd.designmall.mbp.po.PagePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class OmsOrderPageParam {
    @Schema(description = "订单状态，不传则不过滤；0->未支付； 1->已支付；2->已完成；4->已关闭；5->已取消；6->无效订单；")
    @Nullable
    Integer status;

    @Schema(description = "店铺id，不传则不过滤")
    @Nullable
    Long shopId;

    @Schema(description = "产品id，不传则不过滤")
    @Nullable
    Integer productId;

    PagePo pagePo;
}
