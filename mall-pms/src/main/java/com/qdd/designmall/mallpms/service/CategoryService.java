package com.qdd.designmall.mallpms.service;

import com.qdd.designmall.mallpms.po.CategoryAddParam;
import com.qdd.designmall.mallpms.po.CategoryUpdatePo;
import com.qdd.designmall.mallpms.vo.CategoryAllVo;
import com.qdd.designmall.mbp.model.DbPmsCategory;

import java.util.List;

public interface CategoryService {
    void add(CategoryAddParam param);

    void update(CategoryUpdatePo param);

    List<CategoryAllVo> getAll();
}
