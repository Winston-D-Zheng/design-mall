package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @TableName db_qrcode_invitation
 */
@TableName(value ="db_qrcode_validation")
@Data
public class DbQrcodeValidation implements Serializable {
    private Long id;

    private String content;

    /**
     * 扫描状态
     * 0: 未被扫描
     * 1: 已被扫描
     */
    private Integer scanStatus = 0;

    private LocalDateTime createAt;

    @Serial
    private static final long serialVersionUID = 1L;
}