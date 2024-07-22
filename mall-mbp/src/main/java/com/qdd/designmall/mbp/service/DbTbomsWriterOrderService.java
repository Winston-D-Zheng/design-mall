package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

/**
* @author winston
* @description 针对表【db_taobao_order_and_writer】的数据库操作Service
* @createDate 2024-07-11 20:57:51
*/
public interface DbTbomsWriterOrderService extends IService<DbTbomsWriterOrder> {

    List<DbTbomsWriterOrder> listByShopIdAndInIntegratedOrderIds(Long shopId, List<Long> integratedOrderIds);


    IPage<DbTbomsWriterOrder> page(IPage<DbTbomsWriterOrder> page, Long igOrderId, @Nullable Long userId);
}
