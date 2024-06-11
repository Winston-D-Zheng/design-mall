package com.qdd.designmall.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.admin.service.UmsPermissionService;
import com.qdd.designmall.mbp.model.UmsPermission;
import com.qdd.designmall.mbp.mapper.UmsPermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UmsPermissionServiceImpl extends ServiceImpl<UmsPermissionMapper, UmsPermission>
    implements UmsPermissionService {
    private final UmsPermissionMapper umsPermissionMapper;

    @Override
    public List<UmsPermission> queryByAdminId(Long adminId) {
        return umsPermissionMapper.queryByAdminId(adminId);
    }
}




