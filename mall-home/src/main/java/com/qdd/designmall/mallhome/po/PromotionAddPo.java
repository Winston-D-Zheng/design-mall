package com.qdd.designmall.mallhome.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionAddPo {

    private String title;

    private String pic;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long productId;

    @Schema(description = "0-隐藏，1-显示", example = "1")
    private Integer status;
}
