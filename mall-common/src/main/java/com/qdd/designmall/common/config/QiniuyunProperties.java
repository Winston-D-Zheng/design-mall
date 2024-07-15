package com.qdd.designmall.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "qiniuyun")
@Data
public class QiniuyunProperties {
    String domin;
    String accessKey;
    String secretKey;
    String bucket;
}
