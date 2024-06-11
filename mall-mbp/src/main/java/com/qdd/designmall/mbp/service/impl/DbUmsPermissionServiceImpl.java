package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.mapper.UmsPermissionMapper;
import com.qdd.designmall.mbp.model.UmsPermission;
import com.qdd.designmall.mbp.service.DbUmsPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbUmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission>
    implements DbUmsPermissionService {
    private final UmsPermissionMapper umsPermissionMapper;

    @Override
    public List<UmsPermission> queryByAdminId(Long adminId) {
        return umsPermissionMapper.queryByAdminId(adminId);
    }
}




