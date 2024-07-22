package com.qdd.designmall.admin.service;

import java.time.LocalDateTime;

public interface InvitationService {

    /**
     * 生成 写手 邀请码
     *
     * @param shopId 店铺id
     * @return 二维码
     * <p>
     * 1. 店铺id
     * 2. 过期时间
     */
    String generateWriterInviteCode(Long shopId);

    /**
     * 写手扫码
     * @param shopId        店铺id
     * @param expireTime    过期时间
     */
    void scanWriterInviteCode(Long shopId, LocalDateTime expireTime);



    /**
     * 生成 客服 邀请码
     *
     * @param shopId 店铺id
     * @return 二维码
     * <p>
     * 1. 店铺id
     * 2. 过期时间
     * 3. 唯一识别码
     */
    String generateCustomerServiceInviteCode(Long shopId);



    /**
     * 客服扫码
     *
     * @param shopId     店铺id
     * @param expireTime 过期时间
     * @param uniqueId   唯一识别码
     */
    void scanCustomerServiceInviteCode(Long shopId, LocalDateTime expireTime, String uniqueId);
}
