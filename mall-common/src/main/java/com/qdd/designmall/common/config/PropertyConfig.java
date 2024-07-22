package com.qdd.designmall.common.config;

import com.qdd.designmall.common.service.WeiXinService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {
    @Bean
    QiniuyunProperties qiniuyunBean() {
        return new QiniuyunProperties();
    }

    @Bean
    WeixinProperties weixinProperties(){ return new WeixinProperties();}
}
