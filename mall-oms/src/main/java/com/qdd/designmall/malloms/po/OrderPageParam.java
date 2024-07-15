package com.qdd.designmall.malloms.po;

import com.qdd.designmall.mbp.po.PagePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class OrderPageParam {
    @Schema(description = "订单状态，不传则不过滤；0->未支付； 1->已支付；2->已完成；4->已关闭；5->已取消；6->无效订单；")
    @Nullable
    Integer status;

    /**
     * 用户必须传 0
     */
    @Schema(description = "删除状态,不传则不过滤；0->未删除，1->已删除")
    @Nullable
    Integer deleteStatus;

    @Schema(description = "产品id，不传则不过滤")
    @Nullable
    Integer productId;

    /**
     * 商家只能看自己店铺的订单
     */
    @Schema(description = "店铺id，不传则不过滤")
    @Nullable
    Long shopId;

    /**
     * 用户只能看自己的订单
     */
    @Nullable
    Long memberId;

    PagePo page;
}
