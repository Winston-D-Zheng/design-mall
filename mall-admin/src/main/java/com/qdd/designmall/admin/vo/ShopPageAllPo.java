package com.qdd.designmall.admin.vo;

import com.qdd.designmall.mbp.po.PagePo;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ShopPageAllPo {
    @Nullable
    String shopName;
    PagePo page;
}
