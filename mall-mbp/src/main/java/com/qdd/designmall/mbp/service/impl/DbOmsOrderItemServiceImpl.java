package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbOmsOrderItem;
import com.qdd.designmall.mbp.service.DbOmsOrderItemService;
import com.qdd.designmall.mbp.mapper.DbOmsOrderItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【db_oms_order_item】的数据库操作Service实现
* @createDate 2024-06-11 21:10:15
*/
@Service
@RequiredArgsConstructor
public class DbOmsOrderItemServiceImpl extends ServiceImpl<DbOmsOrderItemMapper, DbOmsOrderItem>
    implements DbOmsOrderItemService{
    private final DbOmsOrderItemMapper omsOrderItemMapper;

    @Override
    public DbOmsOrderItem notNullOne(Long orderItemId) {
        DbOmsOrderItem one = getById(orderItemId);
        if (one == null) {
            throw new RuntimeException("订单项不存在");
        }
        return one;
    }

    @Override
    public Long notNullOrderId(Long orderItemId) {
        Long orderId = omsOrderItemMapper.queryOrderId(orderItemId);
        if (orderId == null) {
            throw new RuntimeException("订单项不存在");
        }
        return orderId;
    }
}




