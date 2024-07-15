package com.qdd.designmall.mbp.mapper;

import com.qdd.designmall.mbp.model.UmsMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author winston
* @description 针对表【ums_member(会员表)】的数据库操作Mapper
* @createDate 2024-03-17 20:26:48
* @Entity com.qdd.designmall.mbp.model.UmsMember
*/
public interface DbUmsMemberMapper extends BaseMapper<UmsMember> {

    @Select("select username from ums_admin where id = #{id}")
    String queryUsernameById(Long id);
}




