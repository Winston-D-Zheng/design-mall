package com.qdd.designmall.common.service.impl;

import com.qdd.designmall.common.service.WebsocketService;
import com.qdd.designmall.common.vo.MsgChatVo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebsocketServiceImpl implements WebsocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void topic(String topic, Object data) {
        simpMessagingTemplate.convertAndSend("/topic/" + topic, data);
    }
}