package com.qdd.designmall.admin.service.impl;

import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.common.enums.EAdminRole;
import com.qdd.designmall.common.util.ZBeanUtils;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.service.DbUmsAdminService;
import com.qdd.designmall.security.config.AdminUserDetails;
import com.qdd.designmall.security.po.AdminLoginParam;
import com.qdd.designmall.security.po.MemberLoginParam;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    private final DbUmsAdminService dbUmsAdminService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUserService securityUserService;


    @Override
    public String login(AdminLoginParam param) {

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


    @Override
    public void duplicateThrow(String identifier, int type) {
        boolean exists = isExists(identifier, type);
        if (exists) {
            throw new RuntimeException("该用户名或手机已被注册");
        }
    }

    @Override
    public void addUser(String phone, String password, int type) {
        String username = generateIdentifier(type, () -> {
            // 生成随机用户名
            return UUID.randomUUID().toString().replace("-", "").substring(10);
        });

        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(password);

        UmsAdmin entity = new UmsAdmin();
        entity.setType(type);
        entity.setUsername(username);
        entity.setPassword(encodePassword);
        entity.setPhone(phone);
        // 根据用户类型添加默认权限和uuid
        switch (type) {
            case 0 -> {
                entity.setRoles("WRITER");
                entity.setUuid(generateIdentifier(type, () -> {
                    // 生成随机员工uuid
                    return UUID.randomUUID().toString().replace("-", "");
                }));
            }
            case 1 -> {
                entity.setRoles("MERCHANT");
                entity.setUuid(generateIdentifier(type, () -> {
                    // 生成随机商家uuid
                    return UUID.randomUUID().toString().replace("-", "");
                }));
            }
            default -> throw new RuntimeException("不合法的用户类型：" + type);
        }
        entity.setStatus(1);
        entity.setCreateAt(LocalDateTime.now());
        dbUmsAdminService.save(entity);
    }



    /**
     * 判断 电话号/用户名/uuid 是否已被使用
     *
     * @param identifier 电话号/用户名
     * @param type       {@link UmsAdmin#type}
     */
    private boolean isExists(String identifier, int type) {
        return dbUmsAdminService.lambdaQuery()
                .eq(UmsAdmin::getType, type)
                .and(wrapper -> wrapper
                        .eq(UmsAdmin::getUsername, identifier)
                        .or()
                        .eq(UmsAdmin::getPhone, identifier)
                        .or()
                        .eq(UmsAdmin::getUuid, identifier)
                )
                .exists();
    }


    /**
     * 生成随机用户名，包含校验功能
     *
     * @param type {@link UmsAdmin#type}
     */
    private String generateIdentifier(int type, Supplier<String> identifierSupplier) {

        while (true) {
            String identifier = identifierSupplier.get();
            if (!isExists(identifier, type)) {
                return identifier;
            }
        }
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




