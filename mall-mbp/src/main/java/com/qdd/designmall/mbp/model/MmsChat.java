package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName mms_chat
 */
@TableName(value ="mms_chat")
@Data
public class MmsChat implements Serializable {

    private Long id;

    private Long senderId;

    private Integer senderType;

    private Long receiverId;

    private Integer receiverType;

    private String content;

    private LocalDateTime sendDate;

    private Integer readState;

}