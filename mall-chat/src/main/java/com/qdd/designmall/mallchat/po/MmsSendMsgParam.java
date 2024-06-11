package com.qdd.designmall.mallchat.po;

import lombok.Data;

@Data
public class MmsSendMsgParam {
    Long groupId;
    String payload;
    Integer msgType;
}
