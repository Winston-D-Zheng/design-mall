package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.dto.MmsMsgDto;
import com.qdd.designmall.mbp.model.MmsChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author winston
* @description 针对表【mms_chat_message】的数据库操作Mapper
* @createDate 2024-05-15 13:23:44
* @Entity com.qdd.designmall.mbp.model.MmsChatMessage
*/
public interface MmsChatMessageMapper extends BaseMapper<MmsChatMessage> {
    MmsChatMessage queryLastMsgByGroupId(Long groupId);
    IPage<MmsMsgDto> queryPageMessage(@Param("page") IPage<MmsMsgDto> page, Long groupId, Long userId, Integer userType);
}




