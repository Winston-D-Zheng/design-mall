package com.qdd.designmall.admin.service;

import com.qdd.designmall.admin.po.UserRegisterPo;

public interface UserService {

    //TODO 用户通过注册
    String register(UserRegisterPo param);

    

    //TODO 验证短信验证码
}
