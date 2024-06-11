package com.qdd.designmall.mbp.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;

public class MyPageUtil {
    /**
     * 转化IPage 类型
     * @param origin    原类型IPage
     * @param beanMapper    转化函数
     * @return  新类型IPage
     * @param <OT>  原类型
     * @param <T>   新类型
     */
    public static <OT, T> IPage<T> convertType(IPage<OT> origin, Function<OT, T> beanMapper) {
        Page<T> newPage = new Page<>();

        BeanUtils.copyProperties(origin, newPage);
        List<T> list = origin.getRecords().stream().map(beanMapper).toList();
        newPage.setRecords(list);

        return newPage;
    }
}
