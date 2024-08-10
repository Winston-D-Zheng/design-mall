package com.qdd.designmall.security.service.impl;

import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.security.config.AdminUserDetails;
import com.qdd.designmall.security.config.ZUserDetails;
import com.qdd.designmall.security.po.AdminLoginParam;
import com.qdd.designmall.security.po.MemberLoginParam;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.security.util.JwtTokenProvider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Primary
@ConditionalOnBean(name = "mallAdminApplication")
@RequiredArgsConstructor
@Getter
public class AdminSecurityUserServiceImpl implements SecurityUserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public final EUserType userType = EUserType.ADMIN;

    @Override
    public String login(AdminLoginParam param) {

        String username = param.getIdentifier() + "@" + param.getType();    // abc@0 或 abc@1
        String password = param.getPassword();

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);

        AdminUserDetails principal = (AdminUserDetails) authenticate.getPrincipal();
        return "Bearer " + tokenProvider.generateToken(principal.getUsername());
    }

    @Override
    public ZUserDetails currentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (ZUserDetails) authentication.getPrincipal();
    }
}
