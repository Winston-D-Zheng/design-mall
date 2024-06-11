package com.qdd.designmall.mbp.po;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageParam {
    /**
     * 每页展示条数
     */
    Integer size = 10;
    /**
     * 当前页数，从0开始
     */
    Integer current = 0;
    /**
     * 排序字段
     */
    List<OrderItem> orders = new ArrayList<>();

    public <T> IPage<T> iPage() {
        if (current == null || size == null) {
            throw new RuntimeException("Current page and size cannot be null.");
        }
        Page<T> tPage = new Page<>(current, size);
        if (orders != null) {
            orders.forEach(orderItem ->
                    {
                        // 检查sql注入问题
                        checkSqlInject(orderItem.getColumn());
                        // 将驼峰转下划线
                        orderItem.setColumn(StringUtils.camelToUnderline(orderItem.getColumn()));
                    }
            );
            tPage.setOrders(orders);
        }
        return tPage;
    }

    private void checkSqlInject(String field) {
        boolean check = SqlInjectionUtils.check(field);
        if (check) {
            throw new RuntimeException("存在SQL注入问题");
        }
    }
}