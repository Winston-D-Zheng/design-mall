package com.qdd.designmall.security.service.impl;

import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.security.config.ZUserDetails;
import com.qdd.designmall.security.po.MemberLoginParam;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.security.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSecurityUserServiceImpl implements SecurityUserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    EUserType userType;

    @Bean
    @ConditionalOnBean(name = "mallAdminApplication")
    void setAdmin() {
        this.userType = EUserType.ADMIN;
    }

    @Bean
    @ConditionalOnBean(name = "mallPortalApplication")
    void setMember() {
        this.userType = EUserType.MEMBER;
    }

    @Override
    public String login(MemberLoginParam param) {

        String username = param.getUsername();
        String password = param.getPassword();

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);

        return "Bearer " + tokenProvider.generateToken(username);
    }

    @Override
    public ZUserDetails currentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ZUserDetails) authentication.getPrincipal();
    }
}
