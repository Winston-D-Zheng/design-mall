package com.qdd.designmall.portal.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PmsDetailVo {
    @Schema(description = "商品名称")
    private String name;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "店铺ID")
    private Long shopId;

    @Schema(description = "主图")
    private String pic;

    @Schema(description = "画册图片，连产品图片限制为5张")
    private String albumPics;

    @Schema(description = "发布状态; 0->下架，1->上架")
    private Integer publishStatus;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "销量")
    private Integer sale;

    @Schema(description = "价格")
    private BigDecimal price;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "库存")
    private Integer stock;

    @Schema(description = "单位")
    private String unit;
}
