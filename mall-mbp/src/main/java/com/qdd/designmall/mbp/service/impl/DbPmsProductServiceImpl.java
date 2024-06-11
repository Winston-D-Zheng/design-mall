package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.mapper.PmsProductMapper;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.service.DbPmsProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbPmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct>
    implements DbPmsProductService {
    private final PmsProductMapper pmsProductMapper;


    @Override
    public IPage<PmsProduct> queryByKeyWords(IPage<PmsProduct> page, String keywords) {
        return pmsProductMapper.queryByKeyWords(page,keywords);
    }

    @Override
    public PmsProduct notNullOne(Long productId) {
        PmsProduct one = lambdaQuery().eq(PmsProduct::getId, productId).one();
        if (one != null) {
            return one;
        }
        throw new RuntimeException("商品id=" + productId + "不存在");
    }


}




