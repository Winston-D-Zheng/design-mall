package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbUmsOnline;
import com.qdd.designmall.mbp.service.DbUmsOnlineService;
import com.qdd.designmall.mbp.mapper.DbUmsOnlineMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【ums_online】的数据库操作Service实现
* @createDate 2024-06-15 11:49:32
*/
@Service
public class DbUmsOnlineServiceImpl extends ServiceImpl<DbUmsOnlineMapper, DbUmsOnline>
    implements DbUmsOnlineService {

    @Override
    public DbUmsOnline nullableOne(Integer userTypeValue, Long userId) {
        return lambdaQuery().eq(DbUmsOnline::getUserId, userId).eq(DbUmsOnline::getUserType, userTypeValue).one();
    }
}




