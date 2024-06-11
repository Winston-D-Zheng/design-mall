package com.qdd.designmall.mallpms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mallpms.po.*;
import com.qdd.designmall.mallpms.vo.ProductDetailVo;
import com.qdd.designmall.mbp.model.PmsProduct;

/**
* @author winston
* @description 针对表【pms_product(商品信息)】的数据库操作Service
* @createDate 2024-03-17 14:24:46
*/
public interface ProductService {

    Long create(ProductAddParam param, Long adminId);

    void update(ProductUpdateParam param, Long adminId);

    IPage<PmsProduct> page(ProductPageParam param, Long adminId);

    void delete(ProductDeleteParam param, Long adminId);

    ProductDetailVo detail(Long productId);

    IPage<PmsProduct> search(ProductSearchParam searchParam);

    PmsProduct sale(Long productId, Integer quantity);
}
