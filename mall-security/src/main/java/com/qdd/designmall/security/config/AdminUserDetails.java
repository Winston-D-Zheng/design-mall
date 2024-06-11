package com.qdd.designmall.security.config;

import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.model.UmsPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnBean(name = "mallAdminApplication")
@Data
@AllArgsConstructor
public class AdminUserDetails implements UserDetails {
    private UmsAdmin user;
    private List<UmsPermission> permission;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permission.stream().map(item -> new SimpleGrantedAuthority(item.getValue())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
