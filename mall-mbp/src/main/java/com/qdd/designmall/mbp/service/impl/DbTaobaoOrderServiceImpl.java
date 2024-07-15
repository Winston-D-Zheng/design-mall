package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbTaobaoOrder;
import com.qdd.designmall.mbp.service.DbTaobaoOrderService;
import com.qdd.designmall.mbp.mapper.DbTaobaoOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【db_taobao_order】的数据库操作Service实现
* @createDate 2024-07-14 15:53:52
*/
@Service
public class DbTaobaoOrderServiceImpl extends ServiceImpl<DbTaobaoOrderMapper, DbTaobaoOrder>
    implements DbTaobaoOrderService{

    @Override
    public void saveOrUpdateByShopIdAndOrderNo(DbTaobaoOrder entity) {
        DbTaobaoOrder one = lambdaQuery()
                .eq(DbTaobaoOrder::getShopId, entity.getShopId())
                .eq(DbTaobaoOrder::getOrderNo, entity.getOrderNo()).one();

        if (one == null) {
            save(entity);
        } else {
            entity.setId(one.getId());
            updateById(entity);
        }
    }
}




