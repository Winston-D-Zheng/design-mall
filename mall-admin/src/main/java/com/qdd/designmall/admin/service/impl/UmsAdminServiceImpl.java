package com.qdd.designmall.admin.service.impl;

import com.qdd.designmall.admin.po.UserRegisterPo;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.common.enums.EAdminRole;
import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.service.DbUmsAdminService;
import com.qdd.designmall.security.config.AdminUserDetails;
import com.qdd.designmall.security.po.UserLoginParam;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    private final DbUmsAdminService dbUmsAdminService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;


    @Override
    public void register(UserRegisterPo param) {
        //查询是否有相同用户名的用户
        boolean exists = dbUmsAdminService.lambdaQuery().eq(UmsAdmin::getUsername, param.getUsername()).exists();
        if (exists) {
            throw new RuntimeException("用户已存在");
        }


        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(param.getPassword());


        UmsAdmin umsAdmin = new UmsAdmin();
        ZBeanUtils.copyProperties(param, umsAdmin);
        umsAdmin.setCreateTime(LocalDate.now());
        umsAdmin.setStatus(1);
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
        ZBeanUtils.copyProperties(user, r);

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
        return this.currentUserDetails().getUserId();
    }


    // ********** 身份变更 **********

    @Override
    public void addMerchantRole(String code) {
        //TODO 验证验证码有效性


        // 添加身份权限
        addRole(EAdminRole.MERCHANT);
    }


    @Override
    public void addCustomerServiceRole(String code) {
        //TODO 验证验证码有效性


        // 添加身份权限
        addRole(EAdminRole.CUSTOMER_SERVICE);
    }

    @Override
    public void addWriterRole(String code) {
        //TODO 验证验证码有效性

        // 添加身份权限
        addRole(EAdminRole.WRITER);
    }


    /**
     * 为当前用户添加身份
     *
     * @param role 身份枚举
     */
    private void addRole(EAdminRole role) {
        Long userId = currentUserId();
        String roleName = role.name();

        UmsAdmin admin = dbUmsAdminService.getById(userId);
        String roles = admin.getRoles();

        if (!roles.contains(roleName)) {
            List<String> list = Arrays.asList(roles.split(","));
            list.add(roleName);
            String newRoles = String.join(",", list);
            admin.setRoles(newRoles);
            dbUmsAdminService.updateById(admin);
        }
    }
}




