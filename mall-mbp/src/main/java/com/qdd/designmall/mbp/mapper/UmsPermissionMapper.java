package com.qdd.designmall.mbp.mapper;

import com.qdd.designmall.mbp.model.UmsPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsPermissionMapper extends BaseMapper<UmsPermission> {
    List<UmsPermission> queryByAdminId(@Param("adminId") Long adminId);
}




