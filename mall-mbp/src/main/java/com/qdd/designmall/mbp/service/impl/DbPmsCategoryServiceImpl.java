package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbPmsCategory;
import com.qdd.designmall.mbp.service.DbPmsCategoryService;
import com.qdd.designmall.mbp.mapper.DbPmsCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author winston
 * @description 针对表【db_pms_category】的数据库操作Service实现
 * @createDate 2024-06-06 08:51:10
 */
@Service
public class DbPmsCategoryServiceImpl extends ServiceImpl<DbPmsCategoryMapper, DbPmsCategory>
        implements DbPmsCategoryService {

    @Override
    public DbPmsCategory notNullOne(Long id) {
        Optional<DbPmsCategory> dbPmsCategory = lambdaQuery()
                .eq(DbPmsCategory::getId, id)
                .oneOpt();

        return dbPmsCategory.orElseThrow(() -> new RuntimeException("not found"));
    }
}




