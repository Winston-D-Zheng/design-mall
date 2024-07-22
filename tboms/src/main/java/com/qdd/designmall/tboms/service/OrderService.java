package com.qdd.designmall.tboms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.tboms.po.CustomerServiceOrderInputPo;
import com.qdd.designmall.tboms.po.PageIgOrderPo;
import com.qdd.designmall.tboms.po.PageCsOrderPo;
import com.qdd.designmall.tboms.po.PageWriterOrderPo;

public interface OrderService {
    void input(CustomerServiceOrderInputPo param);

    /**
     * 分页综合订单
     * @param relation 0-店长 1-客服
     */
    IPage<DbTbomsIntegratedOrder> pageIntegratedOrder(PageIgOrderPo param, int relation);


    /**
     * 分页客服订单
     * @param relation 0-店长 1-客服
     */
    IPage<DbTbomsCustomerServiceOrder> pageCsOrder(PageCsOrderPo param, int relation);


    /**
     * 分页写手订单
     * @param relation 0-店长 1-客服
     */
    IPage<DbTbomsWriterOrder> pageWriterOrder(PageWriterOrderPo param, int relation);
}
