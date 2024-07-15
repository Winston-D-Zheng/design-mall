package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbMmsStagingMsg;
import com.qdd.designmall.mbp.service.DbMmsStagingMessageService;
import com.qdd.designmall.mbp.mapper.DbMmsStagingMsgMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author winston
* @description 针对表【db_mms_staging_message】的数据库操作Service实现
* @createDate 2024-06-15 14:10:33
*/
@Service
public class DbMmsStagingMessageServiceImpl extends ServiceImpl<DbMmsStagingMsgMapper, DbMmsStagingMsg>
    implements DbMmsStagingMessageService{
    @Override
    public List<DbMmsStagingMsg> list(Integer userTypeValue, Long userId) {
        return lambdaQuery()
                .eq(DbMmsStagingMsg::getUserType,userTypeValue)
                .eq(DbMmsStagingMsg::getId,userId)
                .list();
    }
}




