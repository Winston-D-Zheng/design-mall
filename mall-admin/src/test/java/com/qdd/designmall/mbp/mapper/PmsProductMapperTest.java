package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.MallAdminApplication;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.po.PageParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest
@ContextConfiguration(classes = {MallAdminApplication.class})
class PmsProductMapperTest {
    @Autowired
    PmsProductMapper pmsProductMapper;
    @Test
    void queryByKeyWords() {
        PageParam pageParam = new PageParam();
        pageParam.setSize(10);
        pageParam.setCurrent(0);

        IPage<PmsProduct> r = pmsProductMapper.queryByKeyWords(pageParam.iPage(), "花衬衫");
        System.out.println(r);
    }
}