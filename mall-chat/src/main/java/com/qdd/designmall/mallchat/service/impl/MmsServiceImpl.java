package com.qdd.designmall.mallchat.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.common.util.ZDateUtils;
import com.qdd.designmall.mallchat.enums.EMsgStatus;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.po.MsgUserData;
import com.qdd.designmall.mallchat.service.MmsService;
import com.qdd.designmall.mallchat.vo.ChatMsgVo;
import com.qdd.designmall.mallchat.vo.GroupMsgVo;
import com.qdd.designmall.common.vo.Notification;
import com.qdd.designmall.mallwebsocket.service.WebsocketService;
import com.qdd.designmall.mbp.dto.MmsGroupInfo;
import com.qdd.designmall.mbp.dto.MmsMsgDto;
import com.qdd.designmall.mbp.mapper.MmsChatGroupMapper;
import com.qdd.designmall.mbp.mapper.MmsChatGroupUserMapper;
import com.qdd.designmall.mbp.mapper.MmsChatMessageMapper;
import com.qdd.designmall.mbp.model.MmsChatGroup;
import com.qdd.designmall.mbp.model.MmsChatGroupUser;
import com.qdd.designmall.mbp.model.MmsChatMessage;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.po.PagePo;
import com.qdd.designmall.mbp.service.DbMmsChatGroupService;
import com.qdd.designmall.mbp.service.DbMmsChatGroupUserService;
import com.qdd.designmall.mbp.service.DbMmsChatMessageService;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MmsServiceImpl implements MmsService {

    private final MmsChatGroupMapper mmsChatGroupMapper;
    private final DbSmsShopService dbSmsShopService;
    private final DbMmsChatGroupService dbMmsChatGroupService;
    private final DbMmsChatGroupUserService dbMmsChatGroupUserService;
    private final DbMmsChatMessageService dbMmsChatMessageService;
    private final MmsChatGroupUserMapper mmsChatGroupUserMapper;
    private final WebsocketService websocketService;
    private final MmsChatMessageMapper mmsChatMessageMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createChatGroup(Long shopId, UserDto userDto) {
        Integer userTypeValue = userDto.getEUserType().value();
        Long userId = userDto.getUserId();

        // 聊天组已存在，直接返回
        Long id = mmsChatGroupMapper.queryByShopAndUser(shopId, userTypeValue, userId);
        if (id != null) {
            return id;
        }
        // 不存在，创建
        SmsShop shop = dbSmsShopService.getById(shopId);
        if (shop == null) {
            throw new RuntimeException("店铺不存在");
        }

        // 创建聊天组
        MmsChatGroup chatGroup = new MmsChatGroup() {{
            setShopId(shopId);
            setAvatar(shop.getPic());
            setCreateTime(LocalDateTime.now());
            setName(shop.getName());
        }};
        dbMmsChatGroupService.save(chatGroup);

        // 创建聊天组-用户关系
        Long groupId = chatGroup.getId();
        mmsChatGroupMapper.saveGroupUser(groupId, userTypeValue, userId);

        return groupId;
    }

    @Override
    public void joinChatGroup(Long groupId, UserDto userDto) {
        Integer userTypeValue = userDto.getEUserType().value();
        Long userId = userDto.getUserId();

        // 用户在group中，返回
        MmsChatGroupUser one = dbMmsChatGroupUserService.nullableOne(groupId,
                userTypeValue,
                userId);
        if (one != null) {
            return;
        }

        // 用户不在，加入
        dbMmsChatGroupUserService.save(new MmsChatGroupUser() {{
            setGroupId(groupId);
            setUserId(userId);
            setUserType(userTypeValue);
        }});


        // 通知所有用户有人加入群聊
        someoneJoin(groupId, userDto);
    }


    @Override
    public void sendMessage(Long groupId, MsgUserData msgUserData, String msg, EMsgType type) {
        Integer userTypeValue = msgUserData.getEUserType().value();
        Long userId = msgUserData.getUserId();

        // 获取chat_group_user id
        MmsChatGroupUser one = dbMmsChatGroupUserService.nullableOne(groupId, userTypeValue, userId);
        if (one == null) {
            throw new RuntimeException("用户不在该群聊中");
        }
        Long groupUserId = one.getId();

        // 保存信息
        MmsChatMessage chatMessage = new MmsChatMessage() {{
            setGroupUserId(groupUserId);
            setType(type.value());
            setMessage(msg);
            setCreateTime(LocalDateTime.now());
            setStatus(0);
            setAvatar(msgUserData.getAvatar());
            setNickname(msgUserData.getNickName());
        }};
        dbMmsChatMessageService.save(chatMessage);

        // 通知所有用户有新消息
        newMessage(groupId, chatMessage);
    }

    @Override
    public IPage<MmsGroupInfo> pageGroup(PagePo pagePo, UserDto userDto) {
        return mmsChatGroupUserMapper.queryByUser(pagePo,
                userDto.getEUserType().value(),
                userDto.getUserId());
    }


    @Override
    public IPage<GroupMsgVo> pageGroupWithLastMsg(PagePo pagePo, UserDto userDto) {
        // 分页该用户加入的组
        IPage<MmsChatGroupUser> groupUsers = dbMmsChatGroupUserService.lambdaQuery()
                .eq(MmsChatGroupUser::getUserId, userDto.getUserId())
                .eq(MmsChatGroupUser::getUserType, userDto.getEUserType().value())
                .page(pagePo.iPage());


        return groupUsers.convert(mmsChatGroupUser -> {
            GroupMsgVo groupMsgVo = new GroupMsgVo();

            // 获取组的头像和名称
            MmsChatGroup group = dbMmsChatGroupService.lambdaQuery()
                    .eq(MmsChatGroup::getId, mmsChatGroupUser.getGroupId())
                    .one();
            groupMsgVo.setData(new GroupMsgVo.GroupData() {{
                setAvatar(group.getAvatar());
                setName(group.getName());
                setShopId(group.getShopId());
                setGroupId(group.getId());
            }});

            groupMsgVo.setUnread(0);

            // 获取最后一条信息
            MmsMsgDto lastMessage = mmsChatMessageMapper.queryLastMsgByGroupId(mmsChatGroupUser.getGroupId());
            // 最后一条信息的用户信息
            if (lastMessage != null) {
                groupMsgVo.setLastMessage(
                        new ChatMsgVo() {{
                            setMessageId(lastMessage.getId());
                            setStatus(EMsgStatus.of(lastMessage.getStatus()));
                            setType(EMsgType.of(lastMessage.getType()));
                            setCreateTime(lastMessage.getCreateTime());
                            setSenderData(
                                    new MsgUserData(
                                            EUserType.of(lastMessage.getUserType()),
                                            lastMessage.getUserId(),
                                            lastMessage.getNickname(),
                                            lastMessage.getAvatar()
                                    ));
                            setPayload(lastMessage.getMessage());
                        }});

                // 设置已读未读状态
                LocalDateTime lastReadTime = mmsChatGroupUser.getLastReadTime();
                if (lastReadTime == null || lastReadTime.isBefore(lastMessage.getCreateTime())) {
                    groupMsgVo.setUnread(1);
                }
            }

            return groupMsgVo;
        });
    }

    @Override
    public IPage<ChatMsgVo> pageMsg(PagePo pagePo, UserDto userDto, Long groupId) {
        // 验证用户是否在该聊天组
        dbMmsChatGroupUserService.notNullOne(groupId, userDto.getEUserType().value(), userDto.getUserId());

        // 获取该聊天组信息
        IPage<MmsMsgDto> msgDtoIPage = mmsChatMessageMapper.queryPageMessage(pagePo.iPage(),
                groupId
        );

        // 更新聊天组最后读取信息时间
        mmsChatGroupUserMapper.updateLastReadTime(groupId, userDto.getEUserType().value(), userDto.getUserId(),
                LocalDateTime.now());


        return msgDtoIPage.convert(item -> {
            ChatMsgVo chatMsgVo = new ChatMsgVo();

            chatMsgVo.setSenderData(
                    new MsgUserData(
                            EUserType.of(item.getUserType()),
                            item.getUserId(),
                            item.getNickname(),
                            item.getAvatar()
                    ));
            chatMsgVo.setMessageId(item.getId());
            chatMsgVo.setStatus(EMsgStatus.of(item.getStatus()));
            chatMsgVo.setType(EMsgType.of(item.getType()));
            chatMsgVo.setPayload(item.getMessage());
            chatMsgVo.setCreateTime(item.getCreateTime());

            return chatMsgVo;
        });
    }

    private void someoneJoin(Long groupId, UserDto user) {
        notification(groupId, new Notification() {{
            setType(2);
            setData(user);
        }});
    }

    private void newMessage(Long groupId, MmsChatMessage msg) {

        notification(groupId, new Notification() {{
            setType(1);
            setData(msg);
        }});
    }


    /**
     * 通知组内用户
     *
     * @param groupId      组id
     * @param notification 通知
     */
    private void notification(Long groupId, Notification notification) {
        // 获取加入该组的所有用户
        List<MmsChatGroupUser> users = dbMmsChatGroupUserService.listByGroupId(groupId);

        users.forEach(u ->
                Thread.ofVirtual().start(() ->
                {
                    EUserType userType = EUserType.of(u.getUserType());
                    Long userId = u.getUserId();

                    websocketService.notifyUser(UserDto.of(userType, userId), notification);
                }));
    }
}
