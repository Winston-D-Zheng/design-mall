package com.qdd.designmall.security.config;

import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.mbp.service.DbUmsMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnBean(name = "mallPortalApplication")
@RequiredArgsConstructor
public class MemberUserDetailService implements UserDetailsService {
    private final DbUmsMemberService dbUmsMemberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UmsMember user = dbUmsMemberService.notNullOne(username);

        return new MemberUserDetails(user);
    }
}
