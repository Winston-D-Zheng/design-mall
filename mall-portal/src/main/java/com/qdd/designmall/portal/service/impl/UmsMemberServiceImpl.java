package com.qdd.designmall.portal.service.impl;

import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.mbp.service.DbUmsMemberService;
import com.qdd.designmall.portal.po.MemberUpdatePo;
import com.qdd.designmall.portal.po.UmsRegisterParam;
import com.qdd.designmall.portal.service.UmsMemberService;
import com.qdd.designmall.security.config.MemberUserDetails;
import com.qdd.designmall.security.po.UserLoginParam;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UmsMemberServiceImpl implements UmsMemberService {
    private final DbUmsMemberService dbUmsMemberService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;


    @Override
    public UmsMember register(UmsRegisterParam param) {
        //查询是否有相同用户名的用户
        if (this.hasRepeatUsername(param.getUsername())) {
            throw new RuntimeException("用户已存在");
        }
        if (this.hasRepeatPhone(param.getPhone())) {
            throw new RuntimeException("该手机号已被使用");
        }

        UmsMember umsMember = new UmsMember();
        ZBeanUtils.copyProperties(param, umsMember);
        umsMember.setCreateTime(LocalDateTime.now());
        umsMember.setStatus(1);

        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsMember.getPassword());
        umsMember.setPassword(encodePassword);
        dbUmsMemberService.save(umsMember);
        return umsMember;
    }

    @Override
    public String login(UserLoginParam param) {
        return securityUserService.login(param);
    }

    @Override
    public UmsMember userInfo() {
        UmsMember r = new UmsMember();

        UmsMember user = currentUser();
        ZBeanUtils.copyProperties(user, r);

        r.setPassword("****");
        return r;
    }

    @Override
    public MemberUserDetails currentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (MemberUserDetails) authentication.getPrincipal();
    }

    @Override
    public UmsMember currentUser() {
        return this.currentUserDetails().getUser();
    }

    @Override
    public Long currentUserId() {
        return this.currentUser().getId();
    }

    @Override
    public boolean hasRepeatUsername(String username) {
        return dbUmsMemberService.lambdaQuery().eq(UmsMember::getUsername, username).exists();
    }

    @Override
    public boolean hasRepeatPhone(String phone) {
        return dbUmsMemberService.lambdaQuery().eq(UmsMember::getPhone, phone).exists();
    }

    @Override
    public void updateInfo(MemberUpdatePo param) {
        UmsMember user = currentUser();
        ZBeanUtils.copyProperties(param,user);
        dbUmsMemberService.updateById(user);
    }
}
