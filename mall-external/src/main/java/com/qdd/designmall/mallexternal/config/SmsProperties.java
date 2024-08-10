package com.qdd.designmall.mallexternal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    /**
     * 重试时间间隔
     */
    Integer refetchInterval;

    /**
     * 过期时长
     */
    Integer expiration;
}
