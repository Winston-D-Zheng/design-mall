package com.qdd.designmall.mallhome.po;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PromotionShopAddPo {
    Long shopId;
    LocalDate startDate;
    LocalDate endDate;
    Integer status;
}
