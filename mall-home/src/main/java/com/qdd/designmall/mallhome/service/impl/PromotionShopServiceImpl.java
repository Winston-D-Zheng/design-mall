package com.qdd.designmall.mallhome.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.PromotionShopAddPo;
import com.qdd.designmall.mallhome.po.PromotionShopPagePo;
import com.qdd.designmall.mallhome.service.PromotionShopService;
import com.qdd.designmall.mbp.model.DbHmsPromotionShop;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.service.DbHmsPromotionShopService;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class PromotionShopServiceImpl implements PromotionShopService {
    private final DbHmsPromotionShopService dbHmsPromotionShopService;
    private final DbSmsShopService dbSmsShopService;

    @Override
    public void add(PromotionShopAddPo param) {
        dbHmsPromotionShopService.checkExistsThrow(param.getShopId());

        SmsShop shop = dbSmsShopService.notNullOne(param.getShopId());

        DbHmsPromotionShop entity = new DbHmsPromotionShop();

        BeanUtils.copyProperties(param, entity);

        entity.setName(shop.getName());
        entity.setPic(shop.getPic());

        dbHmsPromotionShopService.save(entity);
    }

    @Override
    public void delete(Long id) {
        DbHmsPromotionShop entity = dbHmsPromotionShopService.notNullOne(id);
        entity.setStatus(0);
    }

    @Override
    public IPage<DbHmsPromotionShop> page(PromotionShopPagePo param) {
        return dbHmsPromotionShopService.lambdaQuery()
                .eq(param.getStatus() != null, DbHmsPromotionShop::getStatus, param.getStatus())
                .page(param.getPage().iPage());
    }

    @Override
    public List<DbHmsPromotionShop> show() {
        return dbHmsPromotionShopService
                .lambdaQuery()
                .eq(DbHmsPromotionShop::getStatus, 1)
                .list();
    }
}
