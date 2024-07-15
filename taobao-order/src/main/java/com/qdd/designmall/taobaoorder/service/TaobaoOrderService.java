package com.qdd.designmall.taobaoorder.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTaobaoOrderAndWriter;
import com.qdd.designmall.taobaoorder.po.OrderAndWriterInputPo;
import com.qdd.designmall.taobaoorder.po.WriterPageOrderPo;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface TaobaoOrderService {
    void input(OrderAndWriterInputPo param);

    void setShouldPay(Long id,BigDecimal value);

    IPage<DbTaobaoOrderAndWriter> writerPageOrder(WriterPageOrderPo param);

    /**
     * 写手申请订单结算
     * @param id    订单表id
     */
    void settlement(Long id);
}
