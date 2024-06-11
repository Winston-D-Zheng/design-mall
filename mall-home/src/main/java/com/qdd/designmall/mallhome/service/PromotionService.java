package com.qdd.designmall.mallhome.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.PromotionAddPo;
import com.qdd.designmall.mallhome.po.PromotionPagePo;
import com.qdd.designmall.mbp.model.DbHmsPromotion;

import java.util.List;

public interface PromotionService {
    void add(PromotionAddPo param);
    IPage<DbHmsPromotion> page(PromotionPagePo param);

    void delete(Long id);

    List<DbHmsPromotion> show();
}
