package com.qdd.designmall.tboms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.tboms.po.CustomerServiceOrderInputPo;
import com.qdd.designmall.tboms.po.PageIgOrderPo;
import com.qdd.designmall.tboms.po.PageCsOrderPo;
import com.qdd.designmall.tboms.po.PageWriterOrderPo;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

public interface OrderService {

    /**
     * 订单输入
     */
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

    /**
     * 更新综合订单和相关写手订单状态
     * @param igOrderId     综合订单id
     * @param orderState    状态 {@link com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder#orderState}
     */
    void updateIgOrderState(Long igOrderId, int orderState);

    /**
     * 更新综合订单和相关写手订单状态
     * @param igOrder     综合订单
     * @param orderState    状态 {@link com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder#orderState}
     */
    void updateIgOrderState(@NonNull DbTbomsIntegratedOrder igOrder, int orderState);
}
