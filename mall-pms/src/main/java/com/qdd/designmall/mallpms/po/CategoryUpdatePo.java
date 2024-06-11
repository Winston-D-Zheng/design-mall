package com.qdd.designmall.mallpms.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.DbPmsCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryUpdatePo extends DbPmsCategory {
    @JsonIgnore
    private Integer level;
    @JsonIgnore
    private Long productCount;
}
