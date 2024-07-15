package com.qdd.designmall.mbp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MmsMsgDto {
    private Long id;

    private Long userId;

    private Integer userType;

    private Long groupUserId;

    private LocalDateTime createTime;

    private Integer status;

    private String message;

    private Integer type;

    private String nickname;

    private String avatar;
}
