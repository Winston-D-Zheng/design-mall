package com.qdd.designmall.portal.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.po.MmsPageMsgParam;
import com.qdd.designmall.mallchat.po.MmsSendMsgParam;
import com.qdd.designmall.mallchat.po.MsgUserData;
import com.qdd.designmall.mallchat.service.MmsService;
import com.qdd.designmall.mallchat.vo.ChatMsgVo;
import com.qdd.designmall.mallchat.vo.GroupMsgVo;
import com.qdd.designmall.mbp.model.SmsShop;
import com.qdd.designmall.mbp.model.UmsMember;
import com.qdd.designmall.mbp.po.PagePo;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import com.qdd.designmall.portal.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "聊天管理")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Validated
public class MmsChatController {
    private final MmsService mmsService;
    private final UmsMemberService umsMemberService;
    private final DbSmsShopService dbSmsShopService;

    @Operation(summary = "创建聊天")
    @GetMapping("/group/create")
    ResponseEntity<?> createGroup(@RequestParam Long shopId) {
        Long chatGroupId = mmsService.createChatGroup(shopId, getUserDto());

        // 将店长加入聊天组
        SmsShop shop = dbSmsShopService.notNullOne(shopId);
        Long shopOwnerId = shop.getOwnerId();
        mmsService.joinChatGroup(chatGroupId, new UserDto(EUserType.ADMIN, shopOwnerId));

        return ResponseEntity.ok(chatGroupId);
    }


    @Operation(summary = "发送消息")
    @PostMapping("/msg/send")
    ResponseEntity<?> sendMsg(@RequestBody MmsSendMsgParam param) {
        UmsMember user = umsMemberService.currentUser();

        mmsService.sendMessage(param.getGroupId(),
                new MsgUserData(EUserType.MEMBER, user.getId(), user.getNickname(), user.getIcon()),
                param.getPayload(),
                EMsgType.of(param.getMsgType()));

        return ResponseEntity.ok().build();
    }


    @Operation(summary = "获取聊天组列表(带最后一条信息)")
    @PostMapping("/group/page")
    ResponseEntity<?> pageGroup(@RequestBody PagePo pagePo) {

        IPage<GroupMsgVo> r = mmsService.pageGroupWithLastMsg(pagePo, getUserDto());
        return ResponseEntity.ok(r);
    }


    @Operation(summary = "分页聊天消息")
    @PostMapping("/msg/page")
    ResponseEntity<?> pageMessage(@RequestBody MmsPageMsgParam param) {

        IPage<ChatMsgVo> r = mmsService.pageMsg(
                param.getPage(),
                getUserDto(),
                param.getGroupId());
        return ResponseEntity.ok(r);
    }

    private UserDto getUserDto() {
        Long userId = umsMemberService.currentUserId();
        return new UserDto(EUserType.MEMBER, userId);
    }
}
