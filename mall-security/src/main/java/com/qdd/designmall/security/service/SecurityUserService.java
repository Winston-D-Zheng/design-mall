package com.qdd.designmall.security.service;


import com.qdd.designmall.security.config.ZUserDetails;
import com.qdd.designmall.security.po.UserLoginParam;

public interface SecurityUserService {
    String login(UserLoginParam param);
    ZUserDetails currentUserDetails();
}
