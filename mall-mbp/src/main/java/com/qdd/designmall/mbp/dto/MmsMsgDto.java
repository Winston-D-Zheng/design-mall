package com.qdd.designmall.mbp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MmsMsgDto {
    Long userId;
    Integer userType;
    Long messageId;
    LocalDateTime createTime;
    Integer status;
    String message;
    Integer type;
}
