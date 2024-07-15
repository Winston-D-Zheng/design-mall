package com.qdd.designmall.taobaoorder.po;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * 订单验证条件
 */
@Data
public class ValidateConditionPo {
    /**
     * 开始时间
     */
    LocalDateTime startTime;

    /**
     * 结束时间
     */
    LocalDateTime endTime;




}
