package com.qdd.designmall.common.config.property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitMqProperties {
    String host;
    Integer port;
    String username;
    String password;
}
