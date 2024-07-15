package com.qdd.designmall.mallwebsocket.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.vo.Notification;
import com.qdd.designmall.mallwebsocket.service.WebsocketService;
import com.qdd.designmall.mbp.service.DbMmsStagingMessageService;
import com.qdd.designmall.mbp.service.DbUmsOnlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.qdd.designmall.mallwebsocket.util.SocketUserUtils.getSocketUsername;


@Service
@RequiredArgsConstructor
public class WebsocketServiceImpl implements WebsocketService {
    private final DbUmsOnlineService dbUmsOnlineService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DbMmsStagingMessageService dbMmsStagingMessageService;
    private final ObjectMapper objectMapper;


    @Override
    public void notifyUser(UserDto user, Notification data) {

        Integer userTypeValue = user.getEUserType().value();
        Long userId = user.getUserId();

        // 用户在线，发送数据
        String socketUsername = getSocketUsername(user);
        String destination = "/topic/" + socketUsername;
        simpMessagingTemplate.convertAndSend(destination, data);

//        DbUmsOnline onlineUser = dbUmsOnlineService.nullableOne(userTypeValue, userId);
//        if (onlineUser != null) {
//        // 用户在线，发送数据
//        String socketUsername = getSocketUsername(user);
//        String destination = "/queue/" + socketUsername;
//        simpMessagingTemplate.convertAndSend(destination, data);
//        }
//        else {
//            // 用户不在线，信息暂存
//            dbMmsStagingMessageService.save(new DbMmsStagingMsg() {{
//                setUserType(userTypeValue);
//                setUserId(userId);
//                setType(data.getType());
//                try {
//                    setMsg(objectMapper.writeValueAsString(data));
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }});
//        }
    }
}