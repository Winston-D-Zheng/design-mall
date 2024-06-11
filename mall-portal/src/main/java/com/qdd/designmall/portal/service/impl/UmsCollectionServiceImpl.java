package com.qdd.designmall.portal.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.model.UmsProductCollection;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import com.qdd.designmall.mbp.service.DbUmsProductCollectionService;
import com.qdd.designmall.portal.po.UmsProductCollectionPageParam;
import com.qdd.designmall.portal.service.UmsMemberService;
import com.qdd.designmall.portal.service.UmsCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UmsCollectionServiceImpl implements UmsCollectionService {
    private final DbUmsProductCollectionService dbUmsProductCollectionService;
    private final UmsMemberService umsMemberService;
    private final DbPmsProductService dbPmsProductService;

    @Override
    public IPage<UmsProductCollection> page(UmsProductCollectionPageParam param) {
        // 获取当前会员id
        Long userId = umsMemberService.currentUserId();
        return dbUmsProductCollectionService.lambdaQuery()
                // 当前用户
                .eq(UmsProductCollection::getMemberId, userId)
                // 未删除
                .eq(UmsProductCollection::getStatus, 1)
                .page(param.getPageParam().iPage());
    }

    @Override
    public void add(Long productId) {
        // 获取当前会员id
        Long userId = umsMemberService.currentUserId();

        // 已存在更新或直接返回
        var collection = dbUmsProductCollectionService.nullableOne(userId,productId);
        if (collection != null) {
            if (!collection.getStatus().equals(1)) {
                collection.setStatus(1);
                dbUmsProductCollectionService.updateById(collection);
            }
            return;
        }

        collection = new UmsProductCollection();
        collection.setMemberId(userId);
        collection.setProductId(productId);
        PmsProduct pmsProduct = dbPmsProductService.notNullOne(productId);
        collection.setShopId(pmsProduct.getShopId());
        collection.setStatus(1);
        collection.setCreateTime(LocalDateTime.now());
        dbUmsProductCollectionService.save(collection);
    }

    @Override
    public void delete(Long collectionId) {
        Long userId = umsMemberService.currentUserId();
        var productCollection = dbUmsProductCollectionService.lambdaQuery()
                .eq(UmsProductCollection::getMemberId, userId)
                .eq(UmsProductCollection::getId, collectionId)
                .eq(UmsProductCollection::getStatus, 1)
                .one();
        if (productCollection == null) {
            return;
        }
        productCollection.setStatus(0);
        dbUmsProductCollectionService.updateById(productCollection);
    }
}
