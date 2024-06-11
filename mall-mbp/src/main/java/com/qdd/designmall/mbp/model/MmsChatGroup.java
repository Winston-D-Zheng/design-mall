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
 * @TableName mms_chat_group
 */
@TableName(value ="mms_chat_group")
@Data
public class MmsChatGroup implements Serializable {
    private Long id;

    private Long shopId;

    private LocalDateTime createTime;

    private String avatar;

    private String name;
}