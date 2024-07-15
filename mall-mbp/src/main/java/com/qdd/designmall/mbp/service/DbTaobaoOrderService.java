package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbTaobaoOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【db_taobao_order】的数据库操作Service
* @createDate 2024-07-14 15:53:52
*/
public interface DbTaobaoOrderService extends IService<DbTaobaoOrder> {

    void saveOrUpdateByShopIdAndOrderNo(DbTaobaoOrder entity);
}
