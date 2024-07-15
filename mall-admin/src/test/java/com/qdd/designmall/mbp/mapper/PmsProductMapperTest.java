package com.qdd.designmall.mbp.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.MallAdminApplication;
import com.qdd.designmall.mbp.model.PmsProduct;
import com.qdd.designmall.mbp.po.PagePo;
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
        PagePo pagePo = new PagePo();
        pagePo.setSize(10);
        pagePo.setCurrent(0);

        IPage<PmsProduct> r = pmsProductMapper.queryByKeyWords(pagePo.iPage(), "花衬衫");
        System.out.println(r);
    }
}