package com.qdd.designmall.mallchat.vo;


import com.qdd.designmall.mallchat.enums.EMsgStatus;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.po.MsgUserData;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMsgVo {
    Long messageId;
    EMsgType type;
    MsgUserData senderData;
    EMsgStatus status;
    String  payload;
    LocalDateTime createTime;
}

