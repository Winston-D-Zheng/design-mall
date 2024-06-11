package com.qdd.designmall.admin.service.impl;

import com.qdd.designmall.admin.po.UmsAdminParam;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.admin.service.UmsPermissionService;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.service.DbUmsAdminService;
import com.qdd.designmall.security.config.AdminUserDetails;
import com.qdd.designmall.security.po.UserLoginParam;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    private final DbUmsAdminService dbUmsAdminService;
    private final UmsPermissionService umsPermissionService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;


    @Override
    public void register(UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(LocalDate.now());
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        boolean exists = dbUmsAdminService.lambdaQuery().eq(UmsAdmin::getUsername, umsAdmin.getUsername()).exists();
        if (exists) {
            throw new RuntimeException("用户已存在");
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        dbUmsAdminService.save(umsAdmin);
    }

    @Override
    public String login(UserLoginParam param) {
        return securityUserService.login(param);
    }

    @Override
    public UmsAdmin userInfo() {
        UmsAdmin r = new UmsAdmin();

        UmsAdmin user = this.currentUser();
        BeanUtils.copyProperties(user, r);

        r.setPassword("****");

        return r;
    }

    @Override
    public AdminUserDetails currentUserDetails() {
        return (AdminUserDetails) securityUserService.currentUserDetails();
    }

    @Override
    public UmsAdmin currentUser() {
        AdminUserDetails adminUserDetails = this.currentUserDetails();
        return adminUserDetails.getUser();
    }

    @Override
    public Long currentUserId() {
        return this.currentUser().getId();
    }
}




