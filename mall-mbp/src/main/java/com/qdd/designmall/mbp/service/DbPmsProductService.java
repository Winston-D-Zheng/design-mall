package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DbPmsProductService extends IService<PmsProduct> {

    IPage<PmsProduct> queryByKeyWords(IPage<PmsProduct> page, String keywords);

    PmsProduct notNullOne(Long productId);
}
