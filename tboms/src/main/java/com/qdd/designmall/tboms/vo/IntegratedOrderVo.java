package com.qdd.designmall.tboms.vo;

import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import lombok.Data;

import java.util.List;

@Data
public class IntegratedOrderVo {
    DbTbomsIntegratedOrder integratedOrder;
    List<DbTbomsCustomerServiceOrder> csOrders;
    List<DbTbomsWriterOrder> writerOrders;
}
