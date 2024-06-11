package com.qdd.designmall.mallhome.po;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionProductAddPo {
    Long productId;
    LocalDate startDate;
    LocalDate endDate;
    Integer status;
}
