package com.qdd.designmall.security.config;

import com.qdd.designmall.mbp.mapper.UmsPermissionMapper;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.model.UmsPermission;
import com.qdd.designmall.mbp.service.DbUmsAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
@ConditionalOnBean(name = "mallAdminApplication")
@RequiredArgsConstructor
public class AdminUserDetailService implements UserDetailsService {
    private final DbUmsAdminService dbUmsAdminService;
    private final UmsPermissionMapper umsPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UmsAdmin user = dbUmsAdminService.notNullOne(username);
        List<UmsPermission> permissions = umsPermissionMapper.queryByAdminId(user.getId());

        return new AdminUserDetails(user, permissions);
    }
}
