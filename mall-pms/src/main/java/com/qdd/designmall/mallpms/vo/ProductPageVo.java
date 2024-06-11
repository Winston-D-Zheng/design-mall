package com.qdd.designmall.mallpms.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import com.qdd.designmall.mbp.model.PmsProduct;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductPageVo extends PmsProduct {
    @Serial
    private static final long serialVersionUID = 6043062022737333289L;

    @JsonSerialize(using = PicUrlSerializer.class)
    private String picUrl;
    @JsonSerialize(using = PicUrlSerializer.class)
    private String albumPicsUrls;
}
