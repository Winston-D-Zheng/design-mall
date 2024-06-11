package com.qdd.designmall.portal.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MmsPortalRoomCreateParam {
    @Schema(description = "聊天室名称")
    private String name;
    @Schema(description = "聊天室头像")
    private String pic;
    @Schema(description = "店铺联系人id")
    private Long adminId;
}
