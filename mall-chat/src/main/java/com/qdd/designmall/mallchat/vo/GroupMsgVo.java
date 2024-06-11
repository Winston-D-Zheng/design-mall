package com.qdd.designmall.mallchat.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mallchat.enums.EMsgStatus;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

@Data
public class GroupMsgVo {
    GroupData data;
    Integer unread;
    LastMessage lastMessage;

    @Data
    public static class GroupData{
        @JsonSerialize(using = PicUrlSerializer.class)
        String avatar;
        String name;
    }

    @Data
    public static class LastMessage{
        Long timestamp;
        EMsgStatus status;
        EMsgType type;
        Long senderId;
        UserDto senderData;
        String payload;
    }
}
