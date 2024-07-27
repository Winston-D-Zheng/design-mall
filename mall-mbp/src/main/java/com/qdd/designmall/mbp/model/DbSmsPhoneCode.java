package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName db_sms_phone_code
 */
@TableName(value ="db_sms_phone_code")
@Data
public class DbSmsPhoneCode implements Serializable {
    private Long id;

    private String phone;

    private String code;

    /**
     * @see UmsAdmin#type
     */
    private Integer type;

    private Integer status = 0;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Serial
    private static final long serialVersionUID = 1L;
}