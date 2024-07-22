package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【db_shop_user_relation】的数据库操作Service
* @createDate 2024-07-17 16:03:54
*/
public interface DbShopUserRelationService extends IService<DbShopUserRelation> {

    boolean exists(Long shopId, Long userId, int relation);

    void existsThrow(Long shopId, Long userId, int i);

    void notExistsThrow(Long shopId, Long userId, int relation);
}
