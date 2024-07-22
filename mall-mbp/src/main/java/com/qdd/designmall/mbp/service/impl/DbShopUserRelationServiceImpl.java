package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.qdd.designmall.mbp.service.DbShopUserRelationService;
import com.qdd.designmall.mbp.mapper.DbShopUserRelationMapper;
import org.springframework.stereotype.Service;

/**
 * @author winston
 * @description 针对表【db_shop_user_relation】的数据库操作Service实现
 * @createDate 2024-07-17 16:03:54
 */
@Service
public class DbShopUserRelationServiceImpl extends ServiceImpl<DbShopUserRelationMapper, DbShopUserRelation>
        implements DbShopUserRelationService {

    @Override
    public boolean exists(Long shopId, Long userId, int relation) {
        return lambdaQuery()
                .eq(DbShopUserRelation::getShopId, shopId)
                .eq(DbShopUserRelation::getUserId, userId)
                .eq(DbShopUserRelation::getRelation, relation)
                .exists();
    }

    @Override
    public void existsThrow(Long shopId, Long userId, int i) {
        boolean exists = lambdaQuery()
                .eq(DbShopUserRelation::getShopId, shopId)
                .eq(DbShopUserRelation::getUserId, userId)
                .eq(DbShopUserRelation::getRelation, 2)
                .exists();
        if (exists) {
            throw new RuntimeException("该写手已加入店铺");
        }
    }

    @Override
    public void notExistsThrow(Long shopId, Long userId, int relation) {
        boolean exists = lambdaQuery()
                .eq(DbShopUserRelation::getShopId, shopId)
                .eq(DbShopUserRelation::getUserId, userId)
                .eq(DbShopUserRelation::getRelation, relation)
                .exists();
        if (!exists) {
            throw new RuntimeException("该" + getRelationName(relation) + "未加入店铺");
        }
    }

    String getRelationName(int relation) {
        return switch (relation) {
            case 1 -> "客服";
            case 2 -> "写手";
            default -> "未知";
        };
    }
}




