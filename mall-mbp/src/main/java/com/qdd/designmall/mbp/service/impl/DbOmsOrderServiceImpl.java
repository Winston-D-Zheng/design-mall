package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbOmsOrder;
import com.qdd.designmall.mbp.service.DbOmsOrderService;
import com.qdd.designmall.mbp.mapper.DbOmsOrderMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
* @author winston
* @description 针对表【oms_order(订单)】的数据库操作Service实现
* @createDate 2024-03-24 16:01:34
*/
@Service
public class DbOmsOrderServiceImpl extends ServiceImpl<DbOmsOrderMapper, DbOmsOrder>
    implements DbOmsOrderService {

    @Override
    public DbOmsOrder notNullOne(Long orderId) {
        Optional<DbOmsOrder> one = lambdaQuery()
                .eq(DbOmsOrder::getId, orderId)
                .oneOpt();
        return one.orElseThrow(()-> new RuntimeException("订单不存在"));
    }
}




