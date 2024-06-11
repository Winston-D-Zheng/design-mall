package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author winston
* @description 针对表【pms_product(商品信息)】的数据库操作Mapper
* @createDate 2024-03-21 20:52:08
* @Entity com.qdd.designmall.mbp.model.PmsProduct
*/
public interface PmsProductMapper extends BaseMapper<PmsProduct> {
    IPage<PmsProduct> queryByKeyWords(@Param("page") IPage<PmsProduct> page, String keywords);

}




