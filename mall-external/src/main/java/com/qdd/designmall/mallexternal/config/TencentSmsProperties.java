package com.qdd.designmall.mallexternal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tencent-sms")
public class TencentSmsProperties {
    private String secretId;
    private String secretKey;
}
