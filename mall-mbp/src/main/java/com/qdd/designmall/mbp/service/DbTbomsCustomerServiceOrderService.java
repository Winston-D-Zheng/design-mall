package com.qdd.designmall.mbp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTbomsCustomerServiceOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author winston
 * @description 针对表【db_tboms_customer_service_order】的数据库操作Service
 * @createDate 2024-07-16 10:53:04
 */
public interface DbTbomsCustomerServiceOrderService extends IService<DbTbomsCustomerServiceOrder> {
    void existThrow(String taobaoOrderNo, Long shopId);

    List<DbTbomsCustomerServiceOrder> listInTaobaoOrderNos(Long shopId, List<String> taobaoOrderNos);

    List<DbTbomsCustomerServiceOrder> listInIntegratedOrderIds(Long shopId, List<Long> integratedOrderIds);

    IPage<DbTbomsCustomerServiceOrder> page(IPage<DbTbomsCustomerServiceOrder> page, Long integratedOrderId, @Nullable Long userId);

    /**
     * 已被使用的淘宝订单
     *
     * @param shopId        店铺id
     * @param taobaoOrderNos 淘宝订单号
     * @return 已被使用的淘宝订单号
     */
    List<String> taobaoOrderNoInUse(Long shopId, @NonNull List<String> taobaoOrderNos);

    /**
     * 远征淘宝订单是否被使用
     * @param shopId         店铺id
     * @param taobaoOrderNo  淘宝订单号
     */
    boolean isTaobaoInUse(Long shopId, String taobaoOrderNo);
}
