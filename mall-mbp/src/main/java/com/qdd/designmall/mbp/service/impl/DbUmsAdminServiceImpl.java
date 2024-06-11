package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qdd.designmall.mbp.mapper.UmsAdminMapper;
import com.qdd.designmall.mbp.model.UmsAdmin;

import com.qdd.designmall.mbp.service.DbUmsAdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbUmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin>
        implements DbUmsAdminService {

    @Override
    public UmsAdmin notNullOne(String username) {
        UmsAdmin one = lambdaQuery()
                .eq(UmsAdmin::getUsername, username)
                .one();
        if (one == null) {
            throw new RuntimeException("用户不存在");
        }
        if(one.getStatus() != 1) {
            throw new RuntimeException("用户被禁用");
        }
        return one;
    }
}




