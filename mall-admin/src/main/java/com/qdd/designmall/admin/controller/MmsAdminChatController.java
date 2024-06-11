package com.qdd.designmall.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.service.UmsAdminService;
import com.qdd.designmall.common.dto.UserDto;
import com.qdd.designmall.common.enums.EUserType;
import com.qdd.designmall.mallchat.enums.EMsgType;
import com.qdd.designmall.mallchat.po.MmsPageMsgParam;
import com.qdd.designmall.mallchat.po.MmsSendMsgParam;
import com.qdd.designmall.mallchat.service.MmsService;
import com.qdd.designmall.mallchat.vo.ChatMsgVo;
import com.qdd.designmall.mallchat.vo.GroupMsgVo;
import com.qdd.designmall.mbp.po.PageParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "聊天管理")
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class MmsAdminChatController {
    private final UmsAdminService umsAdminService;
    private final MmsService mmsService;

    @Operation(summary = "获取聊天组列表(带最后一条信息)")
    @PostMapping("/group/page")
    ResponseEntity<?> pageGroup(@RequestBody PageParam pageParam) {
        Long userId = umsAdminService.currentUserId();

        IPage<GroupMsgVo> r = mmsService.pageGroupWithLastMsg(pageParam, new UserDto(EUserType.ADMIN, userId));

        return ResponseEntity.ok(r);
    }

    @Operation(summary = "分页聊天消息")
    @PostMapping("/msg/page")
    ResponseEntity<?> pageMessage(@RequestBody MmsPageMsgParam param) {
        Long userId = umsAdminService.currentUserId();

        IPage<ChatMsgVo> r = mmsService.pageMsg(
                param.getPageParam(),
                new UserDto(EUserType.ADMIN, userId),
                param.getGroupId());

        return ResponseEntity.ok(r);
    }

    @Operation(summary = "发送消息")
    @PostMapping("/msg/send")
    ResponseEntity<?> sendMsg(@RequestBody MmsSendMsgParam param) {
        Long userId = umsAdminService.currentUserId();

        mmsService.sendMessage(param.getGroupId(),
                new UserDto(EUserType.ADMIN, userId),
                param.getPayload(),
                EMsgType.of(param.getMsgType()));

        return ResponseEntity.ok().build();
    }
}
