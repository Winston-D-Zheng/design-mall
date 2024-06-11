package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qdd.designmall.mbp.model.UmsAdmin;

/**
 * @author winston
 * @description 针对表【ums_admin(后台用户表)】的数据库操作Service
 * @createDate 2024-03-16 22:24:09
 */
public interface DbUmsAdminService extends IService<UmsAdmin> {
    UmsAdmin notNullOne(String username);
}
