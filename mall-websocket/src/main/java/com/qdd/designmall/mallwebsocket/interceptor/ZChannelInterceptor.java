package com.qdd.designmall.mallwebsocket.interceptor;

import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mbp.model.DbUmsOnline;
import com.qdd.designmall.mbp.service.DbUmsOnlineService;
import com.qdd.designmall.security.config.ZUserDetails;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;

import static com.qdd.designmall.mallwebsocket.util.SocketUserUtils.getSocketUsername;


@Component
@RequiredArgsConstructor
public class ZChannelInterceptor implements ChannelInterceptor {
    private final DbUmsOnlineService dbUmsOnlineService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        StompCommand command = accessor.getCommand();
        switch (command) {
            case SUBSCRIBE -> {
                // 验证用户是否合规
                Authentication authentication = (Authentication) accessor.getUser();
                if (authentication == null) {
                    return null;
                }
                ZUserDetails user = (ZUserDetails) authentication.getPrincipal();
                String destination = accessor.getDestination();
                String needDestination = "/topic/" + getSocketUsername(UserDto.of(user.getUserType(), user.getUserId()));

                // 用户订阅地址与需要订阅地址不符则拒绝订阅
                if (!needDestination.equals(destination)) {
                    return null;
                }
            }
            case CONNECT -> {
                Authentication authentication = (Authentication) accessor.getUser();
                assert authentication != null;
                ZUserDetails user = (ZUserDetails) authentication.getPrincipal();
                // 将用户存入在线用户表
                dbUmsOnlineService.save(new DbUmsOnline() {{
                    setUserId(user.getUserId());
                    setUserType(user.getUserType().value());
                    setLastLoginTime(LocalDateTime.now());
                }});

                //TODO 将离线信息发送给用户

            }
            case DISCONNECT -> {
                // 将用户从在线用户表删除
                Authentication authentication = (Authentication) accessor.getUser();
                assert authentication != null;
                ZUserDetails user = (ZUserDetails) authentication.getPrincipal();

                dbUmsOnlineService.lambdaUpdate()
                        .eq(DbUmsOnline::getUserId, user.getUserId())
                        .eq(DbUmsOnline::getUserType, user.getUserType().value())
                        .remove();
            }
            case null, default -> {
            }
        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECTED.equals(accessor.getCommand())) {
            // 连接成功后添加自定义头信息
            accessor.addNativeHeader("custom-header", "custom-value");
        }
    }
}