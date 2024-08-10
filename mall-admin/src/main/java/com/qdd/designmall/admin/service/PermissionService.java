package com.qdd.designmall.admin.service;

import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.qdd.designmall.mbp.service.DbShopUserRelationService;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("permissionService")
@RequiredArgsConstructor
public class PermissionService {
    private final DbShopUserRelationService dbShopUserRelationService;
    private final SecurityUserService securityUserService;

    /**
     * 当前用户是店铺的店长或客服
     * @param shopId 店铺id
     */
    public boolean isMerchantOrCs(Long shopId){
        Long userId = securityUserService.currentUserDetails().getUserId();
        return dbShopUserRelationService.isMerchantOrCs(shopId, userId);
    }


    /**
     * 当前用户可以修改订单（是店长或客服）
     *
     * @param shopId 店铺id
     */
    public boolean ableToModifyOrder( Long shopId) {
        Long userId = securityUserService.currentUserDetails().getUserId();
        DbShopUserRelation entity = dbShopUserRelationService.getNullableOne(shopId, userId);
        if (entity != null) {
            Integer relation = entity.getRelation();
            return relation == 0 || relation == 1;     // 是店长或客服

        }
        return false;
    }



}
