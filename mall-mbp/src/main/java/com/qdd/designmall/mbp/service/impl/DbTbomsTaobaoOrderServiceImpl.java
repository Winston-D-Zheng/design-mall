package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbTbomsTaobaoOrder;
import com.qdd.designmall.mbp.service.DbTbomsTaobaoOrderService;
import com.qdd.designmall.mbp.mapper.DbTbomsTaobaoOrderMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author winston
 * @description 针对表【db_taobao_order】的数据库操作Service实现
 * @createDate 2024-07-14 15:53:52
 */
@Service
public class DbTbomsTaobaoOrderServiceImpl extends ServiceImpl<DbTbomsTaobaoOrderMapper, DbTbomsTaobaoOrder>
        implements DbTbomsTaobaoOrderService {

    @Override
    public void saveOrUpdateByShopIdAndOrderNo(DbTbomsTaobaoOrder entity) {
        DbTbomsTaobaoOrder one = lambdaQuery()
                .eq(DbTbomsTaobaoOrder::getShopId, entity.getShopId())
                .eq(DbTbomsTaobaoOrder::getTaobaoOrderNo, entity.getTaobaoOrderNo()).one();

        if (one == null) {
            save(entity);
        } else {
            entity.setId(one.getId());
            updateById(entity);
        }
    }

    @Override
    public List<String> listOrderNoInTimeRange(Long shopId, LocalDateTime startTime, LocalDateTime endTime) {
        return lambdaQuery()
                .eq(DbTbomsTaobaoOrder::getShopId, shopId)
                .between(DbTbomsTaobaoOrder::getTaobaoPayTime, startTime, endTime)
                .select(DbTbomsTaobaoOrder::getTaobaoOrderNo)
                .list()
                .stream().map(DbTbomsTaobaoOrder::getTaobaoOrderNo)
                .toList();
    }

    @Override
    public List<DbTbomsTaobaoOrder> listInTaobaoOrderNos(Long shopId, List<String> taobaoOrderNos) {
        return lambdaQuery()
                .eq(DbTbomsTaobaoOrder::getShopId, shopId)
                .in(DbTbomsTaobaoOrder::getTaobaoOrderNo, taobaoOrderNos)
                .list();
    }
}




