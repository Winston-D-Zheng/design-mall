package com.qdd.designmall.mbp.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName db_mms_staging_message
 */
@TableName(value ="db_mms_staging_notification")
@Data
public class DbMmsStagingMsg implements Serializable {
    private Long id;

    private Integer userType;
    private Long userId;

    /**
     * @see com.qdd.designmall.common.vo.Notification#type
     */
    private Integer type;
    private String msg;

}