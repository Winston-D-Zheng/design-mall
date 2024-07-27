package com.qdd.designmall.mallexternal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "weixin")
public class WeixinProperties {
    String secret;
    String appid;
}
