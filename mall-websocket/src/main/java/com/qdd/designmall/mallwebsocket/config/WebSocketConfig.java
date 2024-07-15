package com.qdd.designmall.mallwebsocket.config;

import com.qdd.designmall.mallwebsocket.interceptor.ZChannelInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final ZChannelInterceptor zChannelInterceptor;
    private final RabbitMqProperties rabbitMqProperties;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
        ;
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost(rabbitMqProperties.getHost())
                .setRelayPort(rabbitMqProperties.getPort())
                .setClientLogin(rabbitMqProperties.getUsername())
                .setClientPasscode(rabbitMqProperties.getPassword());
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.interceptors(zChannelInterceptor);
//    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(zChannelInterceptor);
    }

//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new ZChannelInterceptor());
//    }



}