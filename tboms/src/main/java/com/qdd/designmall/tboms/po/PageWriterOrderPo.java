package com.qdd.designmall.tboms.po;

import com.qdd.designmall.mbp.po.PagePo;
import lombok.Data;

/**
 * 分页写手订单
 */
@Data
public class PageWriterOrderPo {
    Long igOrderId;     // 综合订单id
    PagePo page;        // 分页
}
