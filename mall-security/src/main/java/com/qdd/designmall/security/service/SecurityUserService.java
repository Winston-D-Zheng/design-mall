package com.qdd.designmall.security.service;


import com.qdd.designmall.security.config.ZUserDetails;
import com.qdd.designmall.security.po.AdminLoginParam;
import com.qdd.designmall.security.po.MemberLoginParam;

public interface SecurityUserService {
    default String login(MemberLoginParam param) {
        return "";
    }

    default String login(AdminLoginParam param){
        return "";
    }

    ZUserDetails currentUserDetails();
}
