package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbHmsPromotionShop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【db_hms_promotion_shop】的数据库操作Service
* @createDate 2024-06-10 09:59:04
*/
public interface DbHmsPromotionShopService extends IService<DbHmsPromotionShop> {

    boolean checkExists(Long shopId);
    void checkExistsThrow(Long shopId);

    DbHmsPromotionShop notNullOne(Long id);
}
