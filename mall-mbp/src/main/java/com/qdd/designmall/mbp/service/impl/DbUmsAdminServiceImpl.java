package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qdd.designmall.mbp.mapper.DbUmsAdminMapper;
import com.qdd.designmall.mbp.model.UmsAdmin;

import com.qdd.designmall.mbp.service.DbUmsAdminService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DbUmsAdminServiceImpl extends ServiceImpl<DbUmsAdminMapper, UmsAdmin>
        implements DbUmsAdminService {

    @Override
    public UmsAdmin notNullOne(String identifier, int type) {
        UmsAdmin one = lambdaQuery()
                .eq(UmsAdmin::getType, type)
                .and(wrapper-> wrapper
                        .eq(UmsAdmin::getUsername, identifier)
                        .or()
                        .eq(UmsAdmin::getPhone, identifier)
                        .or()
                        .eq(UmsAdmin::getUuid, identifier)
                )
                .one();
        if (one == null) {
            throw new RuntimeException("用户不存在");
        }
        if (one.getStatus() != 1) {
            throw new RuntimeException("用户被禁用");
        }
        return one;
    }

    @Override
    public Long getUserId(String identifier, Integer type) {
        var one = lambdaQuery()
                .eq(type != null, UmsAdmin::getType, type)
                .and(
                        wrapper -> wrapper
                                .eq(UmsAdmin::getUsername, identifier)
                                .or()
                                .eq(UmsAdmin::getPhone, identifier)
                                .or()
                                .eq(UmsAdmin::getUuid, identifier)
                )
                .select(UmsAdmin::getId)
                .one();

        return Objects.isNull(one) ? null : one.getId();
    }
}




