package com.qdd.designmall.mallchat.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qdd.designmall.common.serializer.PicUrlSerializer;
import lombok.Data;

@Data
public class GroupMsgVo {
    GroupData data;
    Integer unread;
    ChatMsgVo lastMessage;


    @Data
    public static class GroupData{
        @JsonSerialize(using = PicUrlSerializer.class)
        String avatar;
        String name;
        Long shopId;
        Long groupId;
    }
}
