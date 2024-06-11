package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.mbp.model.MmsChatGroupUser;
import com.qdd.designmall.mbp.mapper.MmsChatGroupUserMapper;
import com.qdd.designmall.mbp.service.DbMmsChatGroupUserService;
import org.springframework.stereotype.Service;

/**
 * @author winston
 * @description 针对表【mms_chat_group_user】的数据库操作Service实现
 * @createDate 2024-05-27 09:56:36
 */
@Service
public class DbMmsChatGroupUserServiceImpl extends ServiceImpl<MmsChatGroupUserMapper, MmsChatGroupUser>
        implements DbMmsChatGroupUserService {

    @Override
    public MmsChatGroupUser nullableOne(Long groupId, Integer userType, Long userId) {
        return lambdaQuery()
                .eq(MmsChatGroupUser::getGroupId, groupId)
                .eq(MmsChatGroupUser::getUserId, userId)
                .eq(MmsChatGroupUser::getUserType, userType)
                .one();
    }

    @Override
    public MmsChatGroupUser notNullOne(Long groupId, Integer userType, Long userId) {
        MmsChatGroupUser one = lambdaQuery()
                .eq(MmsChatGroupUser::getGroupId, groupId)
                .eq(MmsChatGroupUser::getUserId, userId)
                .eq(MmsChatGroupUser::getUserType, userType)
                .one();

        if (one != null) {
            return one;
        }
        throw new RuntimeException("该用户type=" + EUserType.of(userType) + ",id=" + userId + "不在该群组中；或该群组id=" + groupId + "不存在");
    }
}




