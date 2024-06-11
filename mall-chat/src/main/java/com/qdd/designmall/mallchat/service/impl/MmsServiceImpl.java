package com.qdd.designmall.mallchat.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.common.service.WebsocketService;
import com.qdd.designmall.common.util.ZDateUtils;
import com.qdd.designmall.mallchat.dto.MsgNotification;
import com.qdd.designmall.mallchat.enums.EMsgStatus;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.service.MmsService;
import com.qdd.designmall.mallchat.vo.ChatMsgVo;
import com.qdd.designmall.mallchat.vo.GroupMsgVo;
import com.qdd.designmall.mbp.dto.MmsGroupInfo;
import com.qdd.designmall.mbp.dto.MmsMsgDto;
import com.qdd.designmall.mbp.mapper.MmsChatGroupMapper;
import com.qdd.designmall.mbp.mapper.MmsChatGroupUserMapper;
import com.qdd.designmall.mbp.mapper.MmsChatMessageMapper;
import com.qdd.designmall.mbp.model.MmsChatGroup;
import com.qdd.designmall.mbp.model.MmsChatGroupUser;
import com.qdd.designmall.mbp.model.MmsChatMessage;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.po.PageParam;
import com.qdd.designmall.mbp.service.DbMmsChatGroupService;
import com.qdd.designmall.mbp.service.DbMmsChatGroupUserService;
import com.qdd.designmall.mbp.service.DbMmsChatMessageService;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

        // 已存在，直接返回
        Long id = mmsChatGroupMapper.queryByShopAndUser(shopId, userTypeValue, userId);
        if (id != null) {
            return id;
        }
        // 不存在，创建
        SmsShop shop = dbSmsShopService.getById(shopId);
        if (shop == null) {
            throw new RuntimeException("店铺不存在");
        }
        MmsChatGroup chatGroup = new MmsChatGroup() {{
            setShopId(shopId);
            setAvatar("");
            setCreateTime(LocalDateTime.now());
            setName(shop.getName());
        }};
        dbMmsChatGroupService.save(chatGroup);
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
        peopleJoinNotification(groupId, userDto);
    }


    @Override
    public void sendMessage(Long groupId, UserDto userDto, String msg, EMsgType type) {
        Integer userTypeValue = userDto.getEUserType().value();
        Long userId = userDto.getUserId();

        // 获取chat_group_user id
        MmsChatGroupUser one = dbMmsChatGroupUserService.nullableOne(groupId, userTypeValue, userId);
        Long groupUserId = one.getId();

        MmsChatMessage chatMessage = new MmsChatMessage() {{
            setGroupUserId(groupUserId);
            setType(type.value());
            setMessage(msg);
            setCreateTime(LocalDateTime.now());
            setStatus(0);
        }};
        // 向chat_group_user id 发送消息
        dbMmsChatMessageService.save(chatMessage);

        // 通知所有用户有新消息
        peopleNewMsgNotification(groupId, new MsgNotification() {{
            setUserDto(userDto);
            setMsg(chatMessage);
        }});
    }

    @Override
    public IPage<MmsGroupInfo> pageGroup(PageParam pageParam, UserDto userDto) {
        return mmsChatGroupUserMapper.queryByUser(pageParam,
                userDto.getEUserType().value(),
                userDto.getUserId());
    }


    @Override
    public IPage<GroupMsgVo> pageGroupWithLastMsg(PageParam pageParam, UserDto userDto) {
        // 分页该用户加入的组
        IPage<MmsChatGroupUser> groupUsers = dbMmsChatGroupUserService.lambdaQuery()
                .eq(MmsChatGroupUser::getUserId, userDto.getUserId())
                .eq(MmsChatGroupUser::getUserType, userDto.getEUserType().value())
                .page(pageParam.iPage());


        return groupUsers.convert(mmsChatGroupUser -> {
            GroupMsgVo groupMsgVo = new GroupMsgVo();

            // 获取组的头像和名称
            MmsChatGroup group = dbMmsChatGroupService.lambdaQuery()
                    .eq(MmsChatGroup::getId, mmsChatGroupUser.getGroupId())
                    .one();
            groupMsgVo.setData(new GroupMsgVo.GroupData() {{
                setAvatar(group.getAvatar());
                setName(group.getName());
            }});

            groupMsgVo.setUnread(0);

            // 获取最后一条信息
            MmsChatMessage lastMessage = mmsChatMessageMapper.queryLastMsgByGroupId(mmsChatGroupUser.getGroupId());
            if (lastMessage != null) {
                groupMsgVo.setLastMessage(new GroupMsgVo.LastMessage(){{
                    setTimestamp(ZDateUtils.toTimeStamp(lastMessage.getCreateTime()));
                    setStatus(EMsgStatus.of(lastMessage.getStatus()));
                    setType(EMsgType.of(lastMessage.getType()));
                    setSenderId(mmsChatGroupUser.getUserId());
                    setSenderData(new UserDto() {{
                        setUserId(mmsChatGroupUser.getUserId());
                        setEUserType(EUserType.of(mmsChatGroupUser.getUserType()));
                    }});
                    setPayload(lastMessage.getMessage());
                }});
            }

            return groupMsgVo;
        });
    }

    @Override
    public IPage<ChatMsgVo> pageMsg(PageParam pageParam, UserDto userDto, Long groupId) {
        IPage<MmsMsgDto> msgDtoIPage = mmsChatMessageMapper.queryPageMessage(pageParam.iPage(),
                groupId,
                userDto.getUserId(),
                userDto.getEUserType().value());

        return msgDtoIPage.convert(item -> {
            ChatMsgVo chatMsgVo = new ChatMsgVo();

            chatMsgVo.setSenderId(item.getUserId());
            chatMsgVo.setSenderData(new UserDto() {{
                setUserId(item.getUserId());
                setEUserType(EUserType.of(item.getUserType()));
            }});
            chatMsgVo.setMessageId(item.getMessageId());
            chatMsgVo.setStatus(EMsgStatus.of(item.getStatus()));
            chatMsgVo.setType(EMsgType.of(item.getType()));
            chatMsgVo.setPayload(item.getMessage());

            return chatMsgVo;
        });
    }

    private void peopleJoinNotification(Long groupId, UserDto user) {
        websocketService.topic("peopleJoinNotification" + groupId, user);
    }

    private void peopleNewMsgNotification(Long groupId, MsgNotification notification) {

        websocketService.topic("peopleNewMsgNotification" + groupId, notification);
    }
}
