package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qdd.designmall.mbp.model.UmsAdmin;
import org.springframework.lang.Nullable;

/**
 * @author winston
 * @description 针对表【ums_admin(后台用户表)】的数据库操作Service
 * @createDate 2024-03-16 22:24:09
 */
public interface DbUmsAdminService extends IService<UmsAdmin> {
    /**
     * 获取用户
     * @param identifier    用户信息 电话号/用户名/uuid
     * @param type          用户类型 {@link UmsAdmin#type}
     */
    UmsAdmin notNullOne(String identifier, int type);

    /**
     * 根据 电话号/用户名/uuid ，身份 获取用户
     *
     * @param identifier 电话号/用户名/uuid
     * @param type       用户身份{@link UmsAdmin#type}
     * @return 用户id
     */
    @Nullable
    Long getUserId(String identifier, @Nullable Integer type);
}
