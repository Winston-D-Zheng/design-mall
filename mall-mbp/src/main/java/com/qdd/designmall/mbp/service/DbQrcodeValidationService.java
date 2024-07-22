package com.qdd.designmall.mbp.service;

import com.qdd.designmall.mbp.model.DbQrcodeValidation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author winston
* @description 针对表【db_qrcode_invitation】的数据库操作Service
* @createDate 2024-07-17 14:35:32
*/
public interface DbQrcodeValidationService extends IService<DbQrcodeValidation> {

    void notExistsThrow(String content, int scanStatus);

    DbQrcodeValidation nullableOne(String content);
}
