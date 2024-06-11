package com.qdd.designmall.portal.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MmsChatSendParam {
    @Schema(description = "店铺id")
    Long shopId;
    @Schema(description = "聊天内容")
    String content;
}
