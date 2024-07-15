package com.qdd.designmall.mbp.mapper;

import com.qdd.designmall.mbp.model.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

public interface DbUmsAdminMapper extends BaseMapper<UmsAdmin> {

    @Select("select username from ums_admin where id = #{id}")
    String queryUsernameById(Long id);
}




