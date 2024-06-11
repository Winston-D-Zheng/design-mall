package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.UmsProductCollection;
import com.qdd.designmall.mbp.service.DbUmsProductCollectionService;
import com.qdd.designmall.mbp.mapper.UmsProductCollectionMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【ums_product_collection(会员收藏的商品)】的数据库操作Service实现
* @createDate 2024-03-25 09:41:45
*/
@Service
public class DbUmsProductCollectionServiceImpl extends ServiceImpl<UmsProductCollectionMapper, UmsProductCollection>
    implements DbUmsProductCollectionService {

    @Override
    public UmsProductCollection nullableOne(Long userId, Long productId) {
        return lambdaQuery()
                .eq(UmsProductCollection::getMemberId, userId)
                .eq(UmsProductCollection::getProductId, productId).one();
    }
}




