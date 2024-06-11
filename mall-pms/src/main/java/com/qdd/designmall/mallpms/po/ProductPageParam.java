package com.qdd.designmall.mallpms.po;

import com.qdd.designmall.mbp.po.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ProductPageParam {
    @Schema(description = "删除状态，0->未删除，1->已删除, 不传则不过滤此条件")
    @Nullable
    Integer deleteStatus;

    @Schema(description = "发布状态; 0->下架，1->上架，不传则不过滤此条件")
    @Nullable
    private Integer publishStatus;


    @Schema(description = "类目ID，不传则不过滤此条件")
    @Nullable
    private Long categoryId;

    PageParam pageParam;
}
