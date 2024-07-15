package com.qdd.designmall.mallpms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.service.QiNiuYunService;
import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mallpms.po.*;
import com.qdd.designmall.mallpms.service.ProductService;
import com.qdd.designmall.mallpms.vo.ProductDetailVo;
import com.qdd.designmall.mbp.mapper.PmsProductMapper;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author winston
 * @description 针对表【pms_product(商品信息)】的数据库操作Service实现
 * @createDate 2024-03-17 14:24:46
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final DbPmsProductService dbPmsProductService;
    private final DbSmsShopService dbSmsShopService;
    private final QiNiuYunService qiNiuYunService;
    private final PmsProductMapper pmsProductMapper;

    @Override
    public Long create(ProductAddParam param, Long adminId) {
        SmsShop shop = dbSmsShopService.notNullOneByOwnerId(adminId);
        // 保存商品
        PmsProduct pmsProduct = new PmsProduct();
        ZBeanUtils.copyProperties(param, pmsProduct);
        pmsProduct.setShopId(shop.getId());

        // 保存
        pmsProduct.setDeleteStatus(0);          // 删除状态：未删除
        dbPmsProductService.save(pmsProduct);
        return pmsProduct.getId();
    }

    @Override
    public void update(ProductUpdateParam param, Long adminId) {
        SmsShop shop = dbSmsShopService.notNullOneByOwnerId(adminId);

        PmsProduct product = dbPmsProductService.lambdaQuery()
                .eq(PmsProduct::getId, param.getId())
                .eq(PmsProduct::getShopId, shop.getId())
                .one();
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }

        // 保存到数据库
        ZBeanUtils.copyProperties(param, product);

        try {
            dbPmsProductService.updateById(product);
        } catch (Exception e) {
            throw new RuntimeException("存在以下异常：" + e.getMessage());
        }

        // 删除七牛云图片
        Set<String> newPics = Arrays.stream(param.getAlbumPics().split(",")).collect(Collectors.toSet());
        newPics.add(param.getPic());
        Set<String> oldPics = Arrays.stream(product.getAlbumPics().split(",")).collect(Collectors.toSet());
        oldPics.add(product.getPic());
        oldPics.removeAll(newPics);
        qiNiuYunService.asyncDelPic(oldPics);
    }

    @Override
    public IPage<PmsProduct> page(ProductPageParam param, Long adminId) {
        SmsShop shop = dbSmsShopService.notNullOneByOwnerId(adminId);

        return dbPmsProductService.lambdaQuery()
                .eq(PmsProduct::getShopId, shop.getId())
                .eq(param.getDeleteStatus() != null, PmsProduct::getDeleteStatus, param.getDeleteStatus())
                .eq(param.getPublishStatus() != null, PmsProduct::getPublishStatus, param.getPublishStatus())
                .eq(param.getCategoryId() != null, PmsProduct::getCategoryId, param.getCategoryId())
                .page(param.getPage().iPage());
    }

    @Override
    public void delete(ProductDeleteParam param, Long adminId) {
        SmsShop shop = dbSmsShopService.notNullOneByOwnerId(adminId);

        PmsProduct product = dbPmsProductService.lambdaQuery()
                .eq(PmsProduct::getId, param.getProductId())
                .eq(PmsProduct::getShopId, shop.getId())
                .one();
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        // 删除七牛云图片
        Set<String> pics = Stream.of(product.getAlbumPics().split(",")).collect(Collectors.toSet());
        pics.add(product.getPic());
        qiNiuYunService.asyncDelPic(pics);
        product.setDeleteStatus(1);
        dbPmsProductService.updateById(product);
    }

    @Override
    public ProductDetailVo detail(Long productId) {
        PmsProduct product = dbPmsProductService.getById(productId);
        ProductDetailVo detailVo = new ProductDetailVo();
        ZBeanUtils.copyProperties(product, detailVo);
        return detailVo;
    }

    @Override
    public IPage<PmsProduct> search(ProductSearchParam searchParam) {
        return pmsProductMapper.queryByKeyWords(searchParam.getPage().iPage(),searchParam.getKeywords());
    }

    @Override
    public PmsProduct sale(Long productId, Integer quantity) {
        PmsProduct product = dbPmsProductService.notNullOne(productId);

        // 确认商品状态
        checkStatus(product);
        // 确认库存
        if (product.getStock() < quantity) {
            throw new RuntimeException("商品库存不足");
        }
        // 锁定库存
        product.setStock(product.getStock() - quantity);
        dbPmsProductService.updateById(product);

        return product;
    }

    /**
     * 确认商品状态
     * 1. 是否存在
     * 2. 是否下架
     *
     * @param product 商品数据库对象
     */
    private void checkStatus(PmsProduct product) {
        if (product == null || product.getDeleteStatus().equals(1)) {
            throw new RuntimeException("商品不存在");
        }
        if (!product.getPublishStatus().equals(1)) {
            throw new RuntimeException("商品已下架");
        }
    }
}




