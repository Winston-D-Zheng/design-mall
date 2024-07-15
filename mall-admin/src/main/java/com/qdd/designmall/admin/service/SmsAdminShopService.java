package com.qdd.designmall.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qdd.designmall.admin.po.SmsAdminShopPagePram;
import com.qdd.designmall.admin.po.SmsAdminShopCreateParam;
import com.qdd.designmall.admin.po.SmsAdminShopUpdateParam;
import com.qdd.designmall.admin.vo.ShopPageAllPo;
import com.qdd.designmall.mbp.model.SmsShop;

/**
* @author winston
* @description 针对表【sms_shop(店铺)】的数据库操作Service
* @createDate 2024-03-17 16:21:49
*/
public interface SmsAdminShopService {

    Long create(SmsAdminShopCreateParam param);

    IPage<SmsShop> page(SmsAdminShopPagePram param);

    void update(SmsAdminShopUpdateParam param);

    IPage<SmsShop> pageAll(ShopPageAllPo param);
}
