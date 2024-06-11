package com.qdd.designmall.mallhome.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.PromotionProductPagePo;
import com.qdd.designmall.mallhome.po.PromotionProductAddPo;
import com.qdd.designmall.mallhome.service.PromotionProductService;
import com.qdd.designmall.mbp.model.DbHmsPromotionProduct;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.service.DbHmsPromotionProductService;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class PromotionProductServiceImpl implements PromotionProductService {
    private final DbPmsProductService dbPmsProductService;
    private final DbHmsPromotionProductService dbHmsPromotionProductService;

    @Override
    public void add(PromotionProductAddPo param) {
        dbHmsPromotionProductService.checkExistsThrow(param.getProductId());

        PmsProduct product = dbPmsProductService.notNullOne(param.getProductId());

        DbHmsPromotionProduct entity = new DbHmsPromotionProduct();

        BeanUtils.copyProperties(param, entity);

        entity.setPic(product.getPic());
        entity.setName(product.getName());

        dbHmsPromotionProductService.save(entity);
    }

    @Override
    public void delete(Long id) {
        DbHmsPromotionProduct entity = dbHmsPromotionProductService.notNullOne(id);
        entity.setStatus(0);
        dbHmsPromotionProductService.updateById(entity);
    }

    @Override
    public IPage<DbHmsPromotionProduct> page(PromotionProductPagePo param) {
        return dbHmsPromotionProductService.lambdaQuery()
                .eq(param.getStatus() != null, DbHmsPromotionProduct::getStatus, param.getStatus())
                .page(param.getPage().iPage());
    }

    @Override
    public List<DbHmsPromotionProduct> show() {
        return dbHmsPromotionProductService
                .lambdaQuery()
                .eq(DbHmsPromotionProduct::getStatus, 1)
                .list();
    }
}
