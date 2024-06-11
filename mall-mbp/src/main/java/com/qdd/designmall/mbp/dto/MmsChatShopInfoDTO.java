package com.qdd.designmall.mbp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MmsChatShopInfoDTO {
    Long shopId;
    String shopName;
    String icon;
    /**
     * 最后一条内容
     */
    String content;
    LocalDateTime createTime;
    Integer memberReadStatus;
}
