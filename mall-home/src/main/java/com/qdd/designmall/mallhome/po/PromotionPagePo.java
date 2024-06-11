package com.qdd.designmall.mallhome.po;

import com.qdd.designmall.common.constraint.ZPattern;
import com.qdd.designmall.mbp.po.PageParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class PromotionPagePo {
    @Nullable
    @Schema(description = "状态。不传则不筛选")
    Integer status;
    PageParam page;
}
