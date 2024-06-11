package com.qdd.designmall.mallhome.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.PromotionAddPo;
import com.qdd.designmall.mallhome.po.PromotionPagePo;
import com.qdd.designmall.mallhome.service.PromotionService;
import com.qdd.designmall.mbp.model.DbHmsPromotion;
import com.qdd.designmall.mbp.service.DbHmsPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final DbHmsPromotionService pmsPromotionService;

    @Override
    public void add(PromotionAddPo param) {
        DbHmsPromotion entity = new DbHmsPromotion();
        BeanUtils.copyProperties(param, entity);
        pmsPromotionService.save(entity);
    }

    @Override
    public IPage<DbHmsPromotion> page(PromotionPagePo param) {
        return pmsPromotionService.lambdaQuery()
                .eq(param.getStatus() != null, DbHmsPromotion::getStatus, param.getStatus())
                .page(param.getPage().iPage());
    }

    @Override
    public void delete(Long id) {
        DbHmsPromotion entity = pmsPromotionService.notNullOne(id);
        entity.setStatus(0);
        pmsPromotionService.updateById(entity);
    }

    @Override
    public List<DbHmsPromotion> show() {
        LocalDate now = LocalDate.now();
        return pmsPromotionService.lambdaQuery()
                .eq(DbHmsPromotion::getStatus, 1)
                .ge(DbHmsPromotion::getEndDate, now)
                .le(DbHmsPromotion::getStartDate, now)
                .list();
    }

}
