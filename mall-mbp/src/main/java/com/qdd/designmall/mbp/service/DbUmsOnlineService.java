package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbUmsOnline;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【ums_online】的数据库操作Service
* @createDate 2024-06-15 11:49:32
*/
public interface DbUmsOnlineService extends IService<DbUmsOnline> {

    DbUmsOnline nullableOne(Integer userTypeValue, Long userId);
}
