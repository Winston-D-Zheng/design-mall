package com.qdd.designmall.security.config;

import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.model.UmsPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ConditionalOnBean(name = "mallAdminApplication")
@Data
@AllArgsConstructor
public class AdminUserDetails implements ZUserDetails {
    private UmsAdmin user;
    private List<UmsPermission> permission;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> r = permission.stream().map(item -> new SimpleGrantedAuthority(item.getValue())).collect(Collectors.toList());
        // 添加role
        if (StringUtils.isNoneEmpty(user.getRoles())) {
            String[] rolesArr = user.getRoles().split(",");
            List<SimpleGrantedAuthority> roles = Stream.of(rolesArr).map(item -> new SimpleGrantedAuthority("ROLE_" + item)).toList();
            r.addAll(roles);
        }
        return r;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPhone() + "@" + user.getType();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Long getUserId() {
        return user.getId();
    }

    @Override
    public EUserType getUserType() {
        return EUserType.ADMIN;
    }
}
