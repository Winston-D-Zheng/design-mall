package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbHmsPromotion;
import com.qdd.designmall.mbp.service.DbHmsPromotionService;
import com.qdd.designmall.mbp.mapper.DbHmsPromotionMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【pms_promotion】的数据库操作Service实现
* @createDate 2024-06-05 08:49:17
*/
@Service
public class DbHmsPromotionServiceImpl extends ServiceImpl<DbHmsPromotionMapper, DbHmsPromotion>
    implements DbHmsPromotionService {

    @Override
    public DbHmsPromotion notNullOne(Long id) {
        DbHmsPromotion one = getById(id);
        if (one == null) {
            throw new RuntimeException("推广不存在");
        }
        return one;
    }
}




