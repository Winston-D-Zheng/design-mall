package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【ums_member(会员表)】的数据库操作Service
* @createDate 2024-03-17 20:26:48
*/
public interface DbUmsMemberService extends IService<UmsMember> {

    UmsMember notNullOne(String username);
}
