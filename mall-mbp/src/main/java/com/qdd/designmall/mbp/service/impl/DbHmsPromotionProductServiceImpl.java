package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbHmsPromotionProduct;
import com.qdd.designmall.mbp.service.DbHmsPromotionProductService;
import com.qdd.designmall.mbp.mapper.DbHmsPromotionProductMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【db_hms_promotion_product】的数据库操作Service实现
* @createDate 2024-06-10 09:59:00
*/
@Service
public class DbHmsPromotionProductServiceImpl extends ServiceImpl<DbHmsPromotionProductMapper, DbHmsPromotionProduct>
    implements DbHmsPromotionProductService{

    @Override
    public boolean checkExists(Long productId) {
        return lambdaQuery()
                .eq(DbHmsPromotionProduct::getProductId, productId)
                .eq(DbHmsPromotionProduct::getStatus,1)
                .exists();
    }

    @Override
    public void checkExistsThrow(Long productId) {
        if(checkExists(productId)) {
            throw new RuntimeException("推荐商品已存在");
        }
    }

    @Override
    public DbHmsPromotionProduct notNullOne(Long id) {
        DbHmsPromotionProduct one = getById(id);
        if (one == null) {
            throw new RuntimeException("推荐商品不存在");
        }
        return one;
    }
}




