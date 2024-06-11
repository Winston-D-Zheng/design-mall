package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbHmsPromotionProduct;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【db_hms_promotion_product】的数据库操作Service
* @createDate 2024-06-10 09:59:00
*/
public interface DbHmsPromotionProductService extends IService<DbHmsPromotionProduct> {
    boolean checkExists(Long productId);

    void checkExistsThrow(Long productId);

    DbHmsPromotionProduct notNullOne(Long id);
}
