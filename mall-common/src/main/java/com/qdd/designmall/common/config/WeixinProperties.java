package com.qdd.designmall.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "weixin")
public class WeixinProperties {
    String secret;
    String appid;
}
