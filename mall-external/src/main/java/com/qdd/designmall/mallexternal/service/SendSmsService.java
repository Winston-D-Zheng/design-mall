package com.qdd.designmall.mallexternal.service;

public interface SendSmsService {

    void sentToPhone(String content, String... phones);
}
