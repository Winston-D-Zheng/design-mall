package com.qdd.designmall.security.service;


import com.qdd.designmall.security.po.UserLoginParam;
import org.springframework.security.core.userdetails.UserDetails;

public interface SecurityUserService {
    String login(UserLoginParam param);
    UserDetails currentUserDetails();

    default <T> T currentUser(){
        return null;
    }
}
