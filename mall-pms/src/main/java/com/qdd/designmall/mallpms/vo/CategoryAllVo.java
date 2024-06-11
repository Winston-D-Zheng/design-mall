package com.qdd.designmall.mallpms.vo;

import com.qdd.designmall.mbp.model.DbPmsCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryAllVo extends DbPmsCategory{
    List<CategoryAllVo>  children;
}
