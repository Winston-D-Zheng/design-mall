package com.qdd.designmall.mallpms.service.impl;

import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mallpms.po.CategoryAddParam;
import com.qdd.designmall.mallpms.po.CategoryUpdatePo;
import com.qdd.designmall.mallpms.service.CategoryService;
import com.qdd.designmall.mallpms.vo.CategoryAllVo;
import com.qdd.designmall.mbp.model.DbPmsCategory;
import com.qdd.designmall.mbp.service.DbPmsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final DbPmsCategoryService dbPmsCategoryService;


    @Override
    public void add(CategoryAddParam param) {
        DbPmsCategory entity = new DbPmsCategory();
        ZBeanUtils.copyProperties(param, entity);

        if (param.getParentId() == 0) {
            entity.setLevel(1);
        } else {
            DbPmsCategory father = dbPmsCategoryService.notNullOne(param.getParentId());
            entity.setLevel(father.getLevel() + 1);
        }

        dbPmsCategoryService.save(entity);
    }

    @Override
    public void update(CategoryUpdatePo param) {
        DbPmsCategory self = dbPmsCategoryService.notNullOne(param.getId());
        ZBeanUtils.copyProperties(param, self);
        self.setLevel(0);

        if(param.getParentId() != 0){
            DbPmsCategory parent = dbPmsCategoryService.notNullOne(param.getParentId());
            self.setLevel(parent.getLevel() + 1);
        }

        dbPmsCategoryService.updateById(self);
    }

    @Override
    public List<CategoryAllVo> getAll() {

        List<DbPmsCategory> entityList = dbPmsCategoryService.list();

        return categoryRecursion(0L, entityList);
    }

    List<CategoryAllVo> categoryRecursion(Long fatherId, List<DbPmsCategory> entityList) {
        return entityList.stream().filter(e -> e.getParentId().equals(fatherId))
                .map(e -> {
                    CategoryAllVo vo = new CategoryAllVo();
                    ZBeanUtils.copyProperties(e, vo);
                    vo.setChildren(categoryRecursion(e.getId(), entityList));
                    return vo;
                }).toList();
    }

}
