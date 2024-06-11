package com.qdd.designmall.mallpms.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.PmsProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductUpdateParam extends PmsProduct {
    @Serial
    private static final long serialVersionUID = 6317998689812679151L;

    @JsonIgnore
    private Long shopId;
}
