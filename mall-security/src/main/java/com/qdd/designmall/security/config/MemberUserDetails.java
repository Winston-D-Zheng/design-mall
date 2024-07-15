package com.qdd.designmall.security.config;

import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.mbp.model.UmsMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.GrantedAuthority;


import java.util.Collection;
import java.util.List;

@ConditionalOnBean(name = "mallPortalApplication")
@Data
@AllArgsConstructor
public class MemberUserDetails implements ZUserDetails {
    private UmsMember user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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


    @Override
    public Long getUserId() {
        return user.getId();
    }

    @Override
    public EUserType getUserType() {
        return EUserType.MEMBER;
    }
}
