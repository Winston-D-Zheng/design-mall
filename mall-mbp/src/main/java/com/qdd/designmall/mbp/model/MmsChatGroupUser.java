package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @TableName mms_chat_group_user
 */
@TableName(value ="mms_chat_group_user")
@Data
public class MmsChatGroupUser implements Serializable {
    private Long id;

    private Long groupId;

    private Long userId;

    private Integer userType;

    private LocalDateTime lastReadTime;
}