package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qdd.designmall.mbp.model.SmsShop;

/**
* @author winston
* @description 针对表【sms_shop(店铺)】的数据库操作Service
* @createDate 2024-03-17 16:21:49
*/
public interface DbSmsShopService extends IService<SmsShop> {
    SmsShop notNullOne(Long shopId);

    SmsShop notNullOneByOwnerId(Long ownerId);

    /**
     * 验证店铺是否属于用户
     * @param shopId    店铺id
     * @param ownerId   店长id
     */
    void notExistsThrow(Long shopId, Long ownerId);

    boolean exists(Long shopId, Long userId);

    void notExistsThrow(Long shopId);
}
