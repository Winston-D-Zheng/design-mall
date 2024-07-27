package com.qdd.designmall.mallexternal.service;

public interface SmsService {
    /**
     * 发送注册验证码
     *
     * @param phone 接收手机号
     * @param type  {@link com.qdd.designmall.mbp.model.UmsAdmin#type}
     */
    void sendRegisterCode(String phone, int type);

    /**
     * 验证注册验证码
     *
     * @param phone 接收手机号
     * @param code  验证码
     * @param type  {@link com.qdd.designmall.mbp.model.UmsAdmin#type}
     */
    void validateRegisterCode(String phone, String code, int type);
}
