package com.qdd.designmall.portal.service;

import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.portal.po.MemberUpdatePo;
import com.qdd.designmall.portal.po.UmsRegisterParam;
import com.qdd.designmall.security.config.MemberUserDetails;
import com.qdd.designmall.security.po.UserLoginParam;

public interface UmsMemberService {

    UmsMember register(UmsRegisterParam param);

    String login(UserLoginParam param);

    UmsMember userInfo();

    MemberUserDetails currentUserDetails();
    UmsMember currentUser();

    public Long currentUserId();

    /**
     * 有同名用户
     * @param username 用户名
     * @return 存在返回true
     */
    public boolean hasRepeatUsername(String username);

    /**
     * 有重复手机号
     * @param phone 手机号
     * @return  存在返回true
     */
    public boolean hasRepeatPhone(String phone);

    void updateInfo(MemberUpdatePo param);
}
