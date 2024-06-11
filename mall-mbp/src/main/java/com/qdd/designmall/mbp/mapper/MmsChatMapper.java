package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.dto.MmsChatShopInfoDTO;
import com.qdd.designmall.mbp.model.MmsChat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author winston
* @description 针对表【mms_chat】的数据库操作Mapper
* @createDate 2024-05-14 16:44:06
* @Entity com.qdd.designmall.mbp.model.MmsChat
*/
public interface MmsChatMapper extends BaseMapper<MmsChat> {


    IPage<MmsChatShopInfoDTO> queryMessageList(@Param("page") IPage<MmsChatShopInfoDTO> page,@Param("userId") Long userId);
}




