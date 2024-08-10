package com.qdd.designmall.admin.service;

import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.security.config.AdminUserDetails;
import com.qdd.designmall.security.po.AdminLoginParam;
import com.qdd.designmall.security.po.MemberLoginParam;

/**
 * @author winston
 * @description 针对表【ums_admin(后台用户表)】的数据库操作Service
 * @createDate 2024-03-16 22:24:09
 */
public interface UmsAdminService {

    String login(AdminLoginParam param);

    UmsAdmin userInfo();

    public AdminUserDetails currentUserDetails();

    public UmsAdmin currentUser();

    public Long currentUserId();


    // ********** 身份变更 **********

    /**
     * 成为店长
     *
     * @param code 邀请码
     */
    void addMerchantRole(String code);


    /**
     * 成为客服
     *
     * @param code 邀请码
     */
    void addCustomerServiceRole(String code);


    /**
     * 成为写手
     *
     * @param code 邀请码
     */
    void addWriterRole(String code);


    /**
     * 验证用户是否已被注册（用户名，手机号，uuid）
     *
     * @param identifier 手机号
     * @param type  {@link UmsAdmin#type}
     */
    void duplicateThrow(String identifier, int type);


    void addUser(String phone, String password, int type);
}
