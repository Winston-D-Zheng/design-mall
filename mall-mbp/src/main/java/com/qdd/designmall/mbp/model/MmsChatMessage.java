package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * @TableName mms_chat_message
 */
@TableName(value = "mms_chat_message")
@Data
public class MmsChatMessage implements Serializable {
    private Long id;

    private Long groupUserId;

    private LocalDateTime createTime;

    private Integer status;

    private String message;

    private Integer type;

    private String nickname;

    private String avatar;
}