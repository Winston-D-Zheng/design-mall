package com.qdd.designmall.mbp.mapper;

import com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author winston
* @description 针对表【db_tboms_integrated_order】的数据库操作Mapper
* @createDate 2024-07-16 10:53:08
* @Entity com.qdd.designmall.mbp.model.DbTbomsIntegratedOrder
*/
public interface DbTbomsIntegratedOrderMapper extends BaseMapper<DbTbomsIntegratedOrder> {

    /**
     * 根据条件获取综合订单（时间范围内的淘宝订单对应的客服订单所指向的综合订单）
     *
     * @param shopId    店铺id
     * @param startTime 淘宝订单开始时间
     * @param endTime   淘宝订单结束数据
     */
    List<DbTbomsIntegratedOrder> queryByCondition(Long shopId, LocalDateTime startTime, LocalDateTime endTime);
}




