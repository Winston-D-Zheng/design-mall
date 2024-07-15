package com.qdd.designmall.mallchat.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MmsSendMsgParam {
    Long groupId;
    String payload;
    @Schema(description = "消息类型,1:text,2:image")
    Integer msgType;
}
