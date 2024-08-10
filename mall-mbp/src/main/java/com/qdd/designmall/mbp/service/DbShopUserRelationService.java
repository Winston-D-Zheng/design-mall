package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author winston
 * @description 针对表【db_shop_user_relation】的数据库操作Service
 * @createDate 2024-07-17 16:03:54
 */
public interface DbShopUserRelationService extends IService<DbShopUserRelation> {

    boolean exists(Long shopId, Long userId, int relation);

    void existsThrow(Long shopId, Long userId, int i);

    void notExistsThrow(Long shopId, Long userId, int relation);

    /**
     * 用户是店铺的店长或客服
     *
     * @param shopId 店铺id
     * @param userId 用户id
     */
    boolean isMerchantOrCs(Long shopId, Long userId);

    DbShopUserRelation getNullableOne(Long shopId, Long userId);

    BigDecimal getCsCommissionRate(Long shopId, Long userId);

    /**
     * 判断用户时该店铺的写手
     *
     * @param shopId 店铺
     * @param userId 写手
     */
    boolean isWriter(Long shopId, Long userId);

    /**
     * 获取店铺所有写手的id
     *
     * @param shopId 店铺id
     * @return 该店铺内所有写手的id
     */
    List<Long> writerIdsInShop(Long shopId);
}
