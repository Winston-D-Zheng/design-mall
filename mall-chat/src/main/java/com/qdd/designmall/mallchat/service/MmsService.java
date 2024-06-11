package com.qdd.designmall.mallchat.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.vo.ChatMsgVo;
import com.qdd.designmall.mallchat.vo.GroupMsgVo;
import com.qdd.designmall.mbp.dto.MmsGroupInfo;
import com.qdd.designmall.mbp.po.PageParam;

public interface MmsService {
    // 创建chat group
    Long createChatGroup(Long shopId, UserDto userDto);

    // 加入chat group
    void joinChatGroup(Long groupId, UserDto userDto);

    // 向chat group 发送信息
    void sendMessage(Long groupId, UserDto userDto, String msg, EMsgType type);

    // 根据用户信息获取加入的chat group
    IPage<MmsGroupInfo> pageGroup(PageParam pageParam, UserDto userDto);

    // 获取携带消息的组列表
    IPage<GroupMsgVo> pageGroupWithLastMsg(PageParam pageParam, UserDto userDto);

    IPage<ChatMsgVo> pageMsg(PageParam pageParam, UserDto userDto, Long groupId);

}
