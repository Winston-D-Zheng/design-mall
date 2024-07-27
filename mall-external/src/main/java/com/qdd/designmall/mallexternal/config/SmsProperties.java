package com.qdd.designmall.mallexternal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {
    Integer refetchInterval;
    Integer expiration;
}
