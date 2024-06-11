package com.qdd.designmall.common.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "qiniuyun")
@Data
public class QiniuyunProperties {
    String domin;
    String accessKey;
    String secretKey;
    String bucket;
}
