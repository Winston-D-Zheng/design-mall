package com.qdd.designmall.common.config;

import com.qdd.designmall.common.config.property.RabbitMqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final RabbitMqProperties rabbitMqProperties;

    @Value("${spring.application.name}")
    private String endPoint;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/" + endPoint).setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost(rabbitMqProperties.getHost())
                .setRelayPort(rabbitMqProperties.getPort())
                .setClientLogin(rabbitMqProperties.getUsername())
                .setClientPasscode(rabbitMqProperties.getPassword());
        registry.setApplicationDestinationPrefixes("/app");
    }
}