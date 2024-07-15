package com.qdd.designmall.mbp.mapper;

import com.qdd.designmall.mbp.model.DbOmsOrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author winston
* @description 针对表【db_oms_order_item】的数据库操作Mapper
* @createDate 2024-06-11 21:10:15
* @Entity com.qdd.designmall.mbp.model.DbOmsOrderItem
*/
public interface DbOmsOrderItemMapper extends BaseMapper<DbOmsOrderItem> {

    @Select("select order_id from db_oms_order_item where id = #{orderItemId}")
    Long queryOrderId(Long orderItemId);
}




