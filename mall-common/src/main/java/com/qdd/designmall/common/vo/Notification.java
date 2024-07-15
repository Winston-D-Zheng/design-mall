package com.qdd.designmall.common.vo;

import lombok.Data;

@Data
public class Notification {
    /**
     * 1:用户聊天信息
     * 2:用户加入
     * 3:系统消息
     */
    Integer type;
    Object data;
}
