package com.qdd.designmall.portal.po;

import com.qdd.designmall.mbp.po.PageParam;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchParam {
    @NotBlank(message = "关键字不能为空")
    private String keywords;
    private PageParam pageParam;
}
