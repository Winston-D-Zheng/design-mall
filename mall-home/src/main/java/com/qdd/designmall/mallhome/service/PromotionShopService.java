package com.qdd.designmall.mallhome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.PromotionShopAddPo;
import com.qdd.designmall.mallhome.po.PromotionShopPagePo;
import com.qdd.designmall.mbp.model.DbHmsPromotionShop;

import java.util.List;

/**
 * 推荐店铺
 */
public interface PromotionShopService {
    // 添加
    void add(PromotionShopAddPo param);

    // 删除
    void delete(Long id);

    // 分页
    IPage<DbHmsPromotionShop> page(PromotionShopPagePo param);

    // 展示
    List<DbHmsPromotionShop> show();
}
