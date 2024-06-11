package com.qdd.designmall.mallpms.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.PmsProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDetailVo extends PmsProduct {

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Integer lowStock;

    @JsonIgnore
    private String keywords;
}
