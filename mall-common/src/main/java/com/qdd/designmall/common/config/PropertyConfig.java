package com.qdd.designmall.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {
    @Bean
    QiniuyunProperties qiniuyunBean() {
        return new QiniuyunProperties();
    }
}
