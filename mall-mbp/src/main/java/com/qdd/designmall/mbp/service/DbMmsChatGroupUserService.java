package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.MmsChatGroupUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【mms_chat_group_user】的数据库操作Service
* @createDate 2024-05-27 09:56:36
*/
public interface DbMmsChatGroupUserService extends IService<MmsChatGroupUser> {
    MmsChatGroupUser nullableOne(Long groupId, Integer userType, Long userId);
    MmsChatGroupUser notNullOne(Long groupId, Integer userType, Long userId);
}
