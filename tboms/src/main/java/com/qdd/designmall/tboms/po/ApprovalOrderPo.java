package com.qdd.designmall.tboms.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 客服审批订单
 */
@Data
public class ApprovalOrderPo {
    Long igOrderId;
    @Schema(description = "是否通过")
    boolean pass;
}
