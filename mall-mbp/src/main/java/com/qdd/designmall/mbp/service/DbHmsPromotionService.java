package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbHmsPromotion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【pms_promotion】的数据库操作Service
* @createDate 2024-06-05 08:49:17
*/
public interface DbHmsPromotionService extends IService<DbHmsPromotion> {

    DbHmsPromotion notNullOne(Long id);
}
