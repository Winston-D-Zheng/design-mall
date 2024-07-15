package com.qdd.designmall.mallwebsocket.util;

import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.enums.EUserType;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SocketUserUtils {

    /**
     * 获得socket用户标识
     * @param user  程序用户标识
     */
    public static String getSocketUsername(UserDto user) {
        return user.getEUserType().name() + "_" + user.getUserId();
    }

    /**
     * 获取程序用户标识
     * @param socketUsername socket用户标识
     */
    public static UserDto getUserDto(String socketUsername) {
        EUserType eUserType = EUserType.valueOf(socketUsername.split("_")[0]);
        Long userId = Long.valueOf(socketUsername.replaceFirst(".*_", ""));
        return new UserDto(eUserType, userId);
    }
}
