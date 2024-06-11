package com.qdd.designmall.admin.service;

import com.qdd.designmall.mbp.model.UmsPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author winston
* @description 针对表【ums_permission(后台用户权限表)】的数据库操作Service
* @createDate 2024-03-17 08:50:49
*/
public interface UmsPermissionService extends IService<UmsPermission> {
    List<UmsPermission> queryByAdminId(Long adminId);
}
