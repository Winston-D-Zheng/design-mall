package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qdd.designmall.mbp.mapper.SmsShopMapper;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DbSmsShopServiceImpl extends ServiceImpl<SmsShopMapper, SmsShop>
        implements DbSmsShopService {
    @Override
    public SmsShop notNullOne(Long shopId) {
        var one = lambdaQuery()
                .eq(SmsShop::getId, shopId)
                .one();
        if (one != null) {
            return one;
        }
        throw new RuntimeException("店铺id=" + shopId + "不存在");
    }

    @Override
    public SmsShop notNullOneByOwnerId(Long ownerId) {
        Optional<SmsShop> one = lambdaQuery()
                .eq(SmsShop::getOwnerId, ownerId)
                .oneOpt();
        return one.orElseThrow(() -> new RuntimeException("ownerId = "+ ownerId +"没有店铺"));
    }

    @Override
    public void notExistsThrow(Long shopId, Long ownerId) {
        boolean exists = lambdaQuery()
                .eq(SmsShop::getId, shopId)
                .eq(SmsShop::getOwnerId, ownerId)
                .exists();

        if (!exists) {
            throw new RuntimeException("店铺id=" + shopId + "不属于该用户");
        }
    }

    @Override
    public boolean exists(Long shopId, Long userId) {
        return lambdaQuery()
                .eq(SmsShop::getId, shopId)
                .eq(SmsShop::getOwnerId, userId)
                .exists();
    }

    @Override
    public void notExistsThrow(Long shopId) {
        boolean exists = lambdaQuery()
                .eq(SmsShop::getId, shopId)
                .exists();
        if (!exists){
            throw new RuntimeException("店铺不存在");
        }
    }
}




