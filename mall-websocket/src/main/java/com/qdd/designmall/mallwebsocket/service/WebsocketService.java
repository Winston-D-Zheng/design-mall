package com.qdd.designmall.mallwebsocket.service;


import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.vo.Notification;

public interface WebsocketService {
    void notifyUser(UserDto user, Notification data);
}