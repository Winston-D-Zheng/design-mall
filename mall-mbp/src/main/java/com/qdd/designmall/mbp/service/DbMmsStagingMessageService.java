package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbMmsStagingMsg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author winston
* @description 针对表【db_mms_staging_message】的数据库操作Service
* @createDate 2024-06-15 14:10:33
*/
public interface DbMmsStagingMessageService extends IService<DbMmsStagingMsg> {

    List<DbMmsStagingMsg> list(Integer userTypeValue, Long userId);
}
