package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.dto.MmsGroupInfo;
import com.qdd.designmall.mbp.model.MmsChatGroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qdd.designmall.mbp.po.PageParam;
import org.apache.ibatis.annotations.Param;

/**
* @author winston
* @description 针对表【mms_chat_group_user】的数据库操作Mapper
* @createDate 2024-05-27 09:56:36
* @Entity com.qdd.designmall.mbp.model.MmsChatGroupUser
*/
public interface MmsChatGroupUserMapper extends BaseMapper<MmsChatGroupUser> {

    IPage<MmsGroupInfo> queryByUser(@Param("page") PageParam pageParam, Integer userType, Long userId);
}




