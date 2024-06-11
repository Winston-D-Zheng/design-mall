package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbPmsCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【db_pms_category】的数据库操作Service
* @createDate 2024-06-06 08:51:10
*/
public interface DbPmsCategoryService extends IService<DbPmsCategory> {
    DbPmsCategory notNullOne(Long id);
}
