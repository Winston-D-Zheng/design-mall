package com.qdd.designmall.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.po.SmsAdminShopPagePram;
import com.qdd.designmall.admin.po.SmsAdminShopCreateParam;
import com.qdd.designmall.admin.po.SmsAdminShopUpdateParam;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.admin.vo.ShopPageAllPo;
import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.admin.service.SmsAdminShopService;
import com.qdd.designmall.mbp.service.DbShopUserRelationService;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class SmsAdminShopServiceImpl implements SmsAdminShopService {
    private final UmsAdminService umsAdminService;
    private final DbSmsShopService dbSmsShopService;
    private final DbShopUserRelationService dbShopUserRelationService;

    @Override
    @Transactional
    public Long create(SmsAdminShopCreateParam param) {
        Long userId = umsAdminService.currentUserId();
        LocalDateTime now = LocalDateTime.now();

        SmsShop smsShop = new SmsShop();
        ZBeanUtils.copyProperties(param, smsShop);
        smsShop.setOwnerId(userId);
        smsShop.setCreateTime(now);
        smsShop.setUpdateTime(now);

        try {
            dbSmsShopService.save(smsShop);
            Long shopId = smsShop.getId();

            // 将用户加入用户-商店关系表
            DbShopUserRelation relation = new DbShopUserRelation();
            relation.setShopId(shopId);
            relation.setUserId(userId);
            relation.setRelation(0);
            relation.setCreateAt(now);
            relation.setUpdateAt(now);
            dbShopUserRelationService.save(relation);

            return shopId;
        } catch (Exception e) {
            if (e.toString().contains("Duplicate")) {
                throw new RuntimeException("该用户已拥有店铺，不可再次创建");
            } else {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public IPage<SmsShop> page(SmsAdminShopPagePram smsShopParam) {
        // 获取当前用户
        Long userId = umsAdminService.currentUserId();
        return dbSmsShopService.lambdaQuery()
                .eq(SmsShop::getOwnerId, userId)
                .page(smsShopParam.getPage().iPage());
    }

    @Override
    public void update(SmsAdminShopUpdateParam param) {
        SmsShop smsShop = dbSmsShopService.lambdaQuery()
                .eq(SmsShop::getId, param.getId())
                .eq(SmsShop::getOwnerId, umsAdminService.currentUserId())
                .one();
        if (smsShop == null) {
            throw new RuntimeException("店铺不存在");
        }
        // 验证店名是否被使用
        boolean exists = dbSmsShopService.lambdaQuery()
                .eq(SmsShop::getName, smsShop.getName())
                .exists();
        if (exists) {
            throw new RuntimeException("店铺名称已存在");
        }
        ZBeanUtils.copyProperties(param, smsShop);
        smsShop.setUpdateTime(LocalDateTime.now());

        try {
            dbSmsShopService.updateById(smsShop);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public IPage<SmsShop> pageAll(ShopPageAllPo param) {
        return dbSmsShopService
                .lambdaQuery()
                .like(param.getShopName() != null,SmsShop::getName,param.getShopName())
                .page(param.getPage().iPage());
    }
}




