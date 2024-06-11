package com.qdd.designmall.mbp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MmsChatMemberInfoDTO {
    Long memberId;
    String nickname;
    String icon;
    /**
     * 最后一条内容
     */
    String content;
    LocalDateTime createTime;
    Integer adminReadStatus;
}
