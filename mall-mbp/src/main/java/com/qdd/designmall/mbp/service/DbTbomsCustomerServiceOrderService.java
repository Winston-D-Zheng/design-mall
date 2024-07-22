package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.util.List;

/**
* @author winston
* @description 针对表【db_tboms_customer_service_order】的数据库操作Service
* @createDate 2024-07-16 10:53:04
*/
public interface DbTbomsCustomerServiceOrderService extends IService<DbTbomsCustomerServiceOrder> {
    void existThrow(String taobaoOrderNo, Long shopId);
    List<DbTbomsCustomerServiceOrder> listInTaobaoOrderNos(Long shopId, List<String > taobaoOrderNos);

    List<DbTbomsCustomerServiceOrder> listInIntegratedOrderIds(Long shopId,List<Long> integratedOrderIds);

    IPage<DbTbomsCustomerServiceOrder> page(IPage<DbTbomsCustomerServiceOrder> page, Long integratedOrderId, @Nullable Long userId);
}
