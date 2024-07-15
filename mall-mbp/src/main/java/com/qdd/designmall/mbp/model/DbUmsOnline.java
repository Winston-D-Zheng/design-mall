package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName ums_online
 */
@TableName(value ="db_ums_online")
@Data
public class DbUmsOnline implements Serializable {
    private Long id;

    private Long userId;

    private Integer userType;

    private LocalDateTime lastLoginTime;

}