package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.qdd.designmall.mbp.service.DbTbomsCustomerServiceOrderService;
import com.qdd.designmall.mbp.mapper.DbTbomsCustomerServiceOrderMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author winston
 * @description 针对表【db_tboms_customer_service_order】的数据库操作Service实现
 * @createDate 2024-07-16 10:53:04
 */
@Service
public class DbTbomsCustomerServiceOrderServiceImpl extends ServiceImpl<DbTbomsCustomerServiceOrderMapper, DbTbomsCustomerServiceOrder>
        implements DbTbomsCustomerServiceOrderService {

    @Override
    public void existThrow(String taobaoOrderNo, Long shopId) {

        boolean exists = lambdaQuery()
                .eq(DbTbomsCustomerServiceOrder::getTaobaoOrderNo, taobaoOrderNo)
                .eq(DbTbomsCustomerServiceOrder::getShopId, shopId)
                .exists();
        if (exists) {
            throw new RuntimeException("淘宝订单重复");
        }
    }

    @Override
    public List<DbTbomsCustomerServiceOrder> listInTaobaoOrderNos(Long shopId, List<String> taobaoOrderNos) {
        return lambdaQuery()
                .in(DbTbomsCustomerServiceOrder::getTaobaoOrderNo, taobaoOrderNos)
                .list();
    }

    @Override
    public List<DbTbomsCustomerServiceOrder> listInIntegratedOrderIds(Long shopId, List<Long> integratedOrderIds) {
        return lambdaQuery()
                .eq(DbTbomsCustomerServiceOrder::getShopId, shopId)
                .in(DbTbomsCustomerServiceOrder::getIntegratedOrderId, integratedOrderIds)
                .list();
    }

    @Override
    public IPage<DbTbomsCustomerServiceOrder> page(IPage<DbTbomsCustomerServiceOrder> page, Long integratedOrderId, Long userId) {
        return lambdaQuery()
                .eq(DbTbomsCustomerServiceOrder::getIntegratedOrderId, integratedOrderId)
                .eq(userId != null, DbTbomsCustomerServiceOrder::getUpdaterId, userId)
                .page(page);
    }

    @Override
    public List<String> taobaoOrderNoInUse(Long shopId, @NonNull List<String> taobaoOrderNos) {
        if (taobaoOrderNos.isEmpty()) {
            return List.of();
        }
        return lambdaQuery()
                .in(DbTbomsCustomerServiceOrder::getTaobaoOrderNo, List.of(taobaoOrderNos))
                .select(DbTbomsCustomerServiceOrder::getTaobaoOrderNo)
                .list()
                .stream()
                .map(DbTbomsCustomerServiceOrder::getTaobaoOrderNo)
                .toList();
    }

    @Override
    public boolean isTaobaoInUse(Long shopId, String taobaoOrderNo) {
        if (taobaoOrderNo == null) {
            return false;
        }
        return lambdaQuery()
                .eq(DbTbomsCustomerServiceOrder::getTaobaoOrderNo, taobaoOrderNo)
                .eq(DbTbomsCustomerServiceOrder::getShopId, shopId)
                .exists();
    }
}




