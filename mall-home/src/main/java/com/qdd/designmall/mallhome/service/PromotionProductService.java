package com.qdd.designmall.mallhome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallhome.po.PromotionProductPagePo;
import com.qdd.designmall.mallhome.po.PromotionProductAddPo;
import com.qdd.designmall.mbp.model.DbHmsPromotionProduct;

import java.util.List;

/**
 * 推荐商品
 */
public interface PromotionProductService {
    // 添加
    void add(PromotionProductAddPo param);

    // 删除
    void delete(Long id);

    // 分页
    IPage<DbHmsPromotionProduct> page(PromotionProductPagePo param);

    // 展示
    List<DbHmsPromotionProduct> show();
}
