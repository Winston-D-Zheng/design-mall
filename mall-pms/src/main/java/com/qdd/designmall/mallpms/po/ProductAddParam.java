package com.qdd.designmall.mallpms.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.PmsProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductAddParam extends PmsProduct {
    @Serial
    private static final long serialVersionUID = 7884048713354825966L;

    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long shopId;
}
