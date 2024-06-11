package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbHmsPromotionShop;
import com.qdd.designmall.mbp.service.DbHmsPromotionShopService;
import com.qdd.designmall.mbp.mapper.DbHmsPromotionShopMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【db_hms_promotion_shop】的数据库操作Service实现
* @createDate 2024-06-10 09:59:04
*/
@Service
public class DbHmsPromotionShopServiceImpl extends ServiceImpl<DbHmsPromotionShopMapper, DbHmsPromotionShop>
    implements DbHmsPromotionShopService{

    @Override
    public boolean checkExists(Long shopId) {
        return lambdaQuery()
                .eq(DbHmsPromotionShop::getShopId, shopId)
                .eq(DbHmsPromotionShop::getShopId,1)
                .exists();
    }

    @Override
    public void checkExistsThrow(Long shopId) {
        if(checkExists(shopId)) {
            throw new RuntimeException("推荐店铺已存在");
        }
    }

    @Override
    public DbHmsPromotionShop notNullOne(Long id) {
        DbHmsPromotionShop one = getById(id);
        if (one == null) {
            throw new RuntimeException("推荐店铺不存在");
        }
        return one;
    }
}




