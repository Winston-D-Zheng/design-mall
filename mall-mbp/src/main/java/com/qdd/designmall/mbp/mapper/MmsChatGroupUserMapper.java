package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.dto.MmsGroupInfo;
import com.qdd.designmall.mbp.model.MmsChatGroupUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qdd.designmall.mbp.po.PagePo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * @author winston
 * @description 针对表【mms_chat_group_user】的数据库操作Mapper
 * @createDate 2024-05-27 09:56:36
 * @Entity com.qdd.designmall.mbp.model.MmsChatGroupUser
 */
public interface MmsChatGroupUserMapper extends BaseMapper<MmsChatGroupUser> {

    IPage<MmsGroupInfo> queryByUser(@Param("page") PagePo pagePo, Integer userType, Long userId);

    @Update("update mms_chat_group_user set last_read_time = #{time} " +
            "where group_id = #{groupId} and user_type = #{userType} and user_id = #{userId} ")
    void updateLastReadTime(Long groupId, Integer userType, Long userId, LocalDateTime time);
}




