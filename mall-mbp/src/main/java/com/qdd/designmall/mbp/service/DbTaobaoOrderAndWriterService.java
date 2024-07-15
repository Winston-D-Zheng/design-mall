package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbTaobaoOrderAndWriter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
* @author winston
* @description 针对表【db_taobao_order_and_writer】的数据库操作Service
* @createDate 2024-07-11 20:57:51
*/
public interface DbTaobaoOrderAndWriterService extends IService<DbTaobaoOrderAndWriter> {

    void existsThrow(String taobaoOrderNo);

    void updateShouldPay(Long id, BigDecimal value);
}
