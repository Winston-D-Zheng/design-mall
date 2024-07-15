package com.qdd.designmall.mbp.service.impl;

import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mbp.mapper.DbUmsAdminMapper;
import com.qdd.designmall.mbp.mapper.DbUmsMemberMapper;
import com.qdd.designmall.mbp.service.UserDispatchService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDispatchServiceImpl implements UserDispatchService {
    private final DbUmsAdminMapper dbUmsAdminMapper;
    private final DbUmsMemberMapper dbUmsMemberMapper;


    @Override
    public String getUsername(UserDto user) {
        String username =
        switch (user.getEUserType()) {
            case ADMIN ->  dbUmsAdminMapper.queryUsernameById(user.getUserId());
            case MEMBER -> dbUmsMemberMapper.queryUsernameById(user.getUserId());
            default -> null;
        };

        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("用户id=" + user.getUserId() + "不存在");
        }

        return username;
    }
}
