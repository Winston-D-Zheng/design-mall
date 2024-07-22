package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.Nullable;

/**
 * @author winston
 * @description 针对表【db_tboms_integrated_order】的数据库操作Service
 * @createDate 2024-07-16 10:53:08
 */
public interface DbTbomsIntegratedOrderService extends IService<DbTbomsIntegratedOrder> {

    IPage<DbTbomsIntegratedOrder> page(IPage<DbTbomsIntegratedOrder> page, @Nullable Long shopId, @Nullable Long userId);

    /**
     * 获取综合订单的店铺id，如果不存在抛出异常
     * @param igOrderId 综合订单ID
     */
    Long getNotNullShopIdById(Long igOrderId);
}
