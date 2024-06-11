package com.qdd.designmall.mallhome.po;

import com.qdd.designmall.mbp.po.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class PromotionProductPagePo {
    @Nullable
    @Schema(description = "状态。不传则不筛选")
    Integer status;
    PageParam page;
}
