package com.qdd.designmall.common.service;

import com.qdd.designmall.common.vo.MsgChatVo;
import org.springframework.stereotype.Service;

@Service
public interface WebsocketService {
    void topic(String topic, Object data);
}