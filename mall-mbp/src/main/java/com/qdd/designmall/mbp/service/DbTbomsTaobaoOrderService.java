package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbTbomsTaobaoOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author winston
* @description 针对表【db_taobao_order】的数据库操作Service
* @createDate 2024-07-14 15:53:52
*/
public interface DbTbomsTaobaoOrderService extends IService<DbTbomsTaobaoOrder> {

    void saveOrUpdateByShopIdAndOrderNo(DbTbomsTaobaoOrder entity);

    List<String> listOrderNoInTimeRange(Long shopId, LocalDateTime startTime, LocalDateTime endTime);

    List<DbTbomsTaobaoOrder> listInTaobaoOrderNos(Long shopId, List<String> taobaoOrderNos);
}
