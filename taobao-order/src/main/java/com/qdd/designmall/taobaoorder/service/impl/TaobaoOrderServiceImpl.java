package com.qdd.designmall.taobaoorder.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.mbp.model.DbTaobaoOrderAndWriter;
import com.qdd.designmall.mbp.service.DbTaobaoOrderAndWriterService;
import com.qdd.designmall.security.service.SecurityUserService;
import com.qdd.designmall.taobaoorder.po.OrderAndWriterInputPo;
import com.qdd.designmall.taobaoorder.po.WriterPageOrderPo;
import com.qdd.designmall.taobaoorder.service.TaobaoOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class TaobaoOrderServiceImpl implements TaobaoOrderService {
    private final DbTaobaoOrderAndWriterService dbTaobaoOrderAndWriterService;
    private final SecurityUserService securityUserService;


    @Override
    @Transactional
    public void input(OrderAndWriterInputPo param) {
        // 查重
        dbTaobaoOrderAndWriterService.existsThrow(param.getTaobaoOrderNo());

        // 写入
        DbTaobaoOrderAndWriter entity = new DbTaobaoOrderAndWriter();
        entity.setTaobaoOrderNo(param.getTaobaoOrderNo());                  // 淘宝订单号
        entity.setTaobaoOrderState(0);                                      // 淘宝订单状态
        entity.setWriterId(param.getWriterId());                            // 写手id
        entity.setShouldPay(param.getShouldPay());                          // 应付工资
        entity.setPayState(0);                                              // 工资状态
        // 客服id
        Long userId = securityUserService.currentUserDetails().getUserId();
        entity.setCustomerServiceId(userId);                                // 客服id
        entity.setCreateTime(LocalDateTime.now());                          // 创建时间
        entity.setUpdateTime(LocalDateTime.now());                          // 更新时间
        dbTaobaoOrderAndWriterService.save(entity);
    }

    @Override
    public void setShouldPay(Long id, BigDecimal value) {
        dbTaobaoOrderAndWriterService.updateShouldPay(id, value);
    }

    @Override
    public IPage<DbTaobaoOrderAndWriter> writerPageOrder(WriterPageOrderPo param) {
        Long userId = securityUserService.currentUserDetails().getUserId();

        return dbTaobaoOrderAndWriterService.lambdaQuery()
                .eq(DbTaobaoOrderAndWriter::getWriterId, userId)
                .page(param.getPage().iPage());
    }

    @Override
    @Transactional
    public void settlement(Long id) {
        Long userId = securityUserService.currentUserDetails().getUserId();

        // 验证该订单是否属于该写手
        boolean exists = dbTaobaoOrderAndWriterService
                .lambdaQuery()
                .eq(DbTaobaoOrderAndWriter::getWriterId, userId)
                .eq(DbTaobaoOrderAndWriter::getId, id)
                .exists();
        if (exists) {
            throw new RuntimeException("该订单不属于该写手");
        }

        // 设置订单状态
        dbTaobaoOrderAndWriterService.lambdaUpdate()
                .set(DbTaobaoOrderAndWriter::getPayState, 1)    // 写手申请结算
                .eq(DbTaobaoOrderAndWriter::getId, id)
                .update();
    }
}
