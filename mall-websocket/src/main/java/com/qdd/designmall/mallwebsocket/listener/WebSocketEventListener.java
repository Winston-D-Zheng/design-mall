package com.qdd.designmall.mallwebsocket.listener;

import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mallwebsocket.service.WebsocketService;
import com.qdd.designmall.mbp.service.DbMmsStagingMessageService;
import com.qdd.designmall.mbp.service.DbUmsOnlineService;
import com.qdd.designmall.security.config.ZUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final DbUmsOnlineService dbUmsOnlineService;
    private final DbMmsStagingMessageService dbMmsStagingMessageService;
    private final WebsocketService websocketService;


//    @SneakyThrows
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        UserDto user = getUserDetails(event);
//
//        Integer userTypeValue = user.getEUserType().value();
//        Long userId = user.getUserId();
//
//        // 更新在线用户数据表
//        dbUmsOnlineService.save(new DbUmsOnline() {{
//            setUserId(userId);
//            setUserType(userTypeValue);
//            setLastLoginTime(LocalDateTime.now());
//        }});
//
//
//        // 发送暂存消息
//        List<DbMmsStagingMessage> dbMmsStagingMessages = dbMmsStagingMessageService.list(userTypeValue, userId);
//        if (dbMmsStagingMessages.isEmpty()) return;
//        List<String> msgs = dbMmsStagingMessages.stream().map(DbMmsStagingMessage::getMsg).toList();
//        websocketService.notifyUser(user, msgs);
//
//        // 删除暂存消息
//        dbMmsStagingMessageService.lambdaUpdate()
//                .eq(DbMmsStagingMessage::getUserType, userTypeValue)
//                .eq(DbMmsStagingMessage::getId, userId)
//                .remove();
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//
//        UserDto user = getUserDetails(event);
//
//        // 更新在线用户数据表
//        dbUmsOnlineService.lambdaUpdate()
//                .eq(DbUmsOnline::getUserType, user.getEUserType())
//                .eq(DbUmsOnline::getUserId, user.getUserId())
//                .remove();
//    }

    private UserDto getUserDetails(AbstractSubProtocolEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Authentication authentication = (Authentication) headerAccessor.getHeader("simpUser");
        assert authentication != null;
        ZUserDetails principal = (ZUserDetails) authentication.getPrincipal();
        return UserDto.of(principal.getUserType(), principal.getUserId());
    }
}