package com.qdd.designmall.mallchat.dto;

import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mbp.model.MmsChatMessage;
import lombok.Data;

@Data
public class MsgNotification {
    UserDto userDto;
    MmsChatMessage msg;
}
