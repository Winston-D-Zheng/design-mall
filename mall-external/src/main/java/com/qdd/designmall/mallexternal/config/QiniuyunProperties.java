package com.qdd.designmall.mallexternal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "qiniuyun")
@Data
public class QiniuyunProperties {
    String domin;
    String accessKey;
    String secretKey;
    String bucket;
}
