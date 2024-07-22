package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbTbomsWriterOrder;
import com.qdd.designmall.mbp.service.DbTbomsWriterOrderService;
import com.qdd.designmall.mbp.mapper.DbTbomsWriterOrderMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author winston
 * @description 针对表【db_taobao_order_and_writer】的数据库操作Service实现
 * @createDate 2024-07-11 20:57:51
 */
@Service
public class DbTbomsWriterOrderServiceImpl extends ServiceImpl<DbTbomsWriterOrderMapper, DbTbomsWriterOrder>
        implements DbTbomsWriterOrderService {

    @Override
    public List<DbTbomsWriterOrder> listByShopIdAndInIntegratedOrderIds(Long shopId, List<Long> integratedOrderIds) {
        return lambdaQuery()
                .eq(DbTbomsWriterOrder::getShopId, shopId)
                .in(DbTbomsWriterOrder::getIntegratedOrderId, integratedOrderIds)
                .list();
    }

    @Override
    public IPage<DbTbomsWriterOrder> page(IPage<DbTbomsWriterOrder> page, Long igOrderId, Long userId) {
        return lambdaQuery()
                .eq(DbTbomsWriterOrder::getIntegratedOrderId, igOrderId)
                .eq(userId != null, DbTbomsWriterOrder::getUpdaterId, userId)
                .page(page);
    }
}




