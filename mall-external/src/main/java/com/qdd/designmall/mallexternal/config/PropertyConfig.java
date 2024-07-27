package com.qdd.designmall.mallexternal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {
    @Bean
    QiniuyunProperties qiniuyunBean() {
        return new QiniuyunProperties();
    }

    @Bean
    WeixinProperties weixinProperties() {
        return new WeixinProperties();
    }

    @Bean
    SmsProperties smsProperties() {
        return new SmsProperties();
    }

    @Bean
    TencentSmsProperties tencentSmsProperties(){
        return new TencentSmsProperties();
    }
}
