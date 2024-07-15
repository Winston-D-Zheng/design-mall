package com.qdd.designmall.admin.service;

import com.qdd.designmall.admin.po.UserRegisterPo;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.security.config.AdminUserDetails;
import com.qdd.designmall.security.po.UserLoginParam;

/**
 * @author winston
 * @description 针对表【ums_admin(后台用户表)】的数据库操作Service
 * @createDate 2024-03-16 22:24:09
 */
public interface UmsAdminService{

    void register(UserRegisterPo userRegisterPo);

    String login(UserLoginParam param);

    UmsAdmin userInfo();

    public AdminUserDetails currentUserDetails();
    public UmsAdmin currentUser();

    public Long currentUserId();

}
