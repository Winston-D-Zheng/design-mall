package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qdd.designmall.mbp.model.UmsPermission;

import java.util.List;

/**
* @author winston
* @description 针对表【ums_permission(后台用户权限表)】的数据库操作Service
* @createDate 2024-03-17 08:50:49
*/
public interface DbUmsPermissionService extends IService<UmsPermission> {
    List<UmsPermission> queryByAdminId(Long adminId);
}
