package com.qdd.designmall.mallpms.po;

import com.qdd.designmall.mbp.po.PagePo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductSearchParam {
    @NotBlank(message = "关键字不能为空")
    private String keywords;
    private PagePo page;
}
