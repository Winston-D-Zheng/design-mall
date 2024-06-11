package com.qdd.designmall.mallpms.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qdd.designmall.mbp.model.DbPmsCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryAddParam extends DbPmsCategory {
    @JsonIgnore
    private Long id;
    @JsonIgnore
    private Integer level;
    @JsonIgnore
    private Long productCount;
}
