package com.qdd.designmall.mallchat.vo;


import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.enums.EMsgStatus;
import lombok.Data;

@Data
public class ChatMsgVo {
    Long messageId;
    EMsgType type;
    Long senderId;
    UserDto senderData;
    EMsgStatus status;
    String  payload;
}

