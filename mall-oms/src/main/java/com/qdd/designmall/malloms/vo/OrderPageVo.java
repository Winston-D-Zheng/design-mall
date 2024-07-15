package com.qdd.designmall.malloms.vo;

import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.model.DbOmsOrderItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderPageVo extends DbOmsOrder {
    List<DbOmsOrderItem> orderItems;
}
