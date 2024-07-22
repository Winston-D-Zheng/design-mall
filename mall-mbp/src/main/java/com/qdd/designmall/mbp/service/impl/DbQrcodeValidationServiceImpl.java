package com.qdd.designmall.mbp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qdd.designmall.mbp.model.DbQrcodeValidation;
import com.qdd.designmall.mbp.service.DbQrcodeValidationService;
import com.qdd.designmall.mbp.mapper.DbQrcodeValidationMapper;
import org.springframework.stereotype.Service;

/**
* @author winston
* @description 针对表【db_qrcode_invitation】的数据库操作Service实现
* @createDate 2024-07-17 14:35:32
*/
@Service
public class DbQrcodeValidationServiceImpl extends ServiceImpl<DbQrcodeValidationMapper, DbQrcodeValidation>
    implements DbQrcodeValidationService {

    @Override
    public void notExistsThrow(String content, int scanStatus) {
        boolean exists = lambdaQuery().eq(DbQrcodeValidation::getContent, content).eq(DbQrcodeValidation::getScanStatus, scanStatus).exists();

        if (!exists) {
            throw new RuntimeException("二维码已被使用");
        }
    }

    @Override
    public DbQrcodeValidation nullableOne(String content) {
        return lambdaQuery().eq(DbQrcodeValidation::getContent, content).one();
    }
}




