package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.UmsAdmin;
import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.mbp.service.DbUmsMemberService;
import com.qdd.designmall.mbp.mapper.UmsMemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【ums_member(会员表)】的数据库操作Service实现
* @createDate 2024-03-17 20:26:48
*/
@Service
public class DbUmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember>
    implements DbUmsMemberService {

    @Override
    public UmsMember notNullOne(String username) {

        UmsMember one = lambdaQuery()
                .eq(UmsMember::getUsername, username)
                .one();
        if (one == null) {
            throw new RuntimeException("用户不存在");
        }
        if(one.getStatus() != 1) {
            throw new RuntimeException("用户被禁用");
        }
        return one;
    }
}




