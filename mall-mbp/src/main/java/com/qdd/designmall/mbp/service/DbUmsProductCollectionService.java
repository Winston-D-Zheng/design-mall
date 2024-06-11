package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.UmsProductCollection;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【ums_product_collection(会员收藏的商品)】的数据库操作Service
* @createDate 2024-03-25 09:41:45
*/
public interface DbUmsProductCollectionService extends IService<UmsProductCollection> {

    UmsProductCollection nullableOne(Long userId, Long productId);
}
