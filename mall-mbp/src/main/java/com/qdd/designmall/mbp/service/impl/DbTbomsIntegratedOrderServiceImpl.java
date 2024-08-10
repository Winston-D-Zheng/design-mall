package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.qdd.designmall.mbp.service.DbTbomsIntegratedOrderService;
import com.qdd.designmall.mbp.mapper.DbTbomsIntegratedOrderMapper;
import org.springframework.stereotype.Service;

/**
 * @author winston
 * @description 针对表【db_tboms_integrated_order】的数据库操作Service实现
 * @createDate 2024-07-16 10:53:08
 */
@Service
public class DbTbomsIntegratedOrderServiceImpl extends ServiceImpl<DbTbomsIntegratedOrderMapper, DbTbomsIntegratedOrder>
        implements DbTbomsIntegratedOrderService {

    @Override
    public IPage<DbTbomsIntegratedOrder> page(IPage<DbTbomsIntegratedOrder> page, Long shopId, Long userId) {
        return lambdaQuery().eq(shopId != null, DbTbomsIntegratedOrder::getShopId, shopId)
                .eq(userId != null, DbTbomsIntegratedOrder::getUpdaterId, userId)
                .page(page);
    }

    @Override
    public Long getNonNullShopIdById(Long igOrderId) {
        DbTbomsIntegratedOrder integratedOrder = lambdaQuery().eq(DbTbomsIntegratedOrder::getId, igOrderId).select(DbTbomsIntegratedOrder::getShopId).one();
        if (integratedOrder == null) {
            throw new RuntimeException("订单id=" + igOrderId + "不存在");
        }
        return integratedOrder.getShopId();
    }
}




