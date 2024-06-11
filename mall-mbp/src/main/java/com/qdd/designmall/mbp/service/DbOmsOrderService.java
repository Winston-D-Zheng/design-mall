package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【oms_order(订单)】的数据库操作Service
* @createDate 2024-03-24 16:01:34
*/
public interface DbOmsOrderService extends IService<DbOmsOrder> {
    DbOmsOrder notNullOne(Long orderId);

}
