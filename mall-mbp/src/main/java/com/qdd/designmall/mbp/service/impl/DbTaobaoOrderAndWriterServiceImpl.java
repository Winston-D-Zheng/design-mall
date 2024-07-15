package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbTaobaoOrderAndWriter;
import com.qdd.designmall.mbp.service.DbTaobaoOrderAndWriterService;
import com.qdd.designmall.mbp.mapper.DbTaobaoOrderAndWriterMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author winston
 * @description 针对表【db_taobao_order_and_writer】的数据库操作Service实现
 * @createDate 2024-07-11 20:57:51
 */
@Service
public class DbTaobaoOrderAndWriterServiceImpl extends ServiceImpl<DbTaobaoOrderAndWriterMapper, DbTaobaoOrderAndWriter>
        implements DbTaobaoOrderAndWriterService {

    @Override
    public void existsThrow(String taobaoOrderNo) {
        boolean exists = lambdaQuery().eq(DbTaobaoOrderAndWriter::getTaobaoOrderNo, taobaoOrderNo).exists();
        if (exists) {
            throw new RuntimeException("订单已存在");
        }
    }

    @Override
    public void updateShouldPay(Long id, BigDecimal value) {
        lambdaUpdate()
                .set(DbTaobaoOrderAndWriter::getShouldPay, value)
                .set(DbTaobaoOrderAndWriter::getUpdateTime, LocalDateTime.now())
                .eq(DbTaobaoOrderAndWriter::getId, id).update();

    }
}




