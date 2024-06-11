package com.qdd.designmall.portal.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.UmsProductCollection;
import com.qdd.designmall.portal.po.UmsProductCollectionPageParam;

public interface UmsCollectionService {

    IPage<UmsProductCollection> page(UmsProductCollectionPageParam param);

    void add(Long productId);

    void delete(Long collectionId);
}
