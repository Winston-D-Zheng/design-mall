package com.qdd.designmall.mbp.mapper;

import com.qdd.designmall.mbp.model.MmsChatGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author winston
* @description 针对表【mms_chat_group】的数据库操作Mapper
* @createDate 2024-05-15 13:23:33
* @Entity com.qdd.designmall.mbp.model.MmsChatGroup
*/
public interface MmsChatGroupMapper extends BaseMapper<MmsChatGroup> {

    Long queryByShopAndUser(Long shopId, Integer userType, Long userId);

    void saveGroupUser(Long groupId, Integer userType, Long userId);
}




