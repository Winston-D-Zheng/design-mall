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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Primary
@ConditionalOnBean(name = "mallAdminApplication")
@RequiredArgsConstructor
public class AdminUserDetailService implements UserDetailsService {
    private final DbUmsAdminService dbUmsAdminService;
    private final UmsPermissionMapper umsPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String identifierWithType) throws UsernameNotFoundException {
        // 校验用户是否存在
        String regex = "^(.+)@([01])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(identifierWithType);

        if (!matcher.matches()) {
            throw new RuntimeException("格式不正确");
        }

        String identifier = matcher.group(1);
        int type = Integer.parseInt(matcher.group(2));


        UmsAdmin user = dbUmsAdminService.notNullOne(identifier, type);
        List<UmsPermission> permissions = umsPermissionMapper.queryByAdminId(user.getId());

        return new AdminUserDetails(user, permissions);
    }
}
