package com.qdd.designmall.mallexternal.service.impl;

import com.qdd.designmall.mallexternal.config.SmsProperties;
import com.qdd.designmall.mallexternal.service.SendSmsService;
import com.qdd.designmall.mallexternal.service.SmsService;
import com.qdd.designmall.mbp.model.DbSmsPhoneCode;
import com.qdd.designmall.mbp.service.DbSmsPhoneCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService {
    private final DbSmsPhoneCodeService dbSmsPhoneCodeService;
    private final SmsProperties smsProperties;
    private final SendSmsService sendSmsService;

    @Override
    public void sendRegisterCode(String phone, int type) {
        LocalDateTime now = LocalDateTime.now();

        Optional<DbSmsPhoneCode> oneOpt = dbSmsPhoneCodeService.lambdaQuery()
                .eq(DbSmsPhoneCode::getPhone, phone)
                .eq(DbSmsPhoneCode::getType, type)
                .oneOpt();

        // 验证是否过于频繁
        if (oneOpt.isPresent()) {
            DbSmsPhoneCode one = oneOpt.get();
            LocalDateTime updateAt = one.getUpdateAt();
            Integer refetchInterval = smsProperties.getRefetchInterval();
            if (updateAt.plusSeconds(refetchInterval).isAfter(now)) {
                throw new RuntimeException("请求过于频繁");
            }
        }

        // 生成新的验证码，更新/保存到数据库
        String code = generateCode();
        if (oneOpt.isPresent()) {
            DbSmsPhoneCode one = oneOpt.get();
            one.setCode(code);
            one.setStatus(0);
            one.setUpdateAt(now);
            dbSmsPhoneCodeService.updateById(one);
        } else {
            DbSmsPhoneCode one = new DbSmsPhoneCode();
            one.setPhone(phone);
            one.setCode(code);
            one.setType(type);
            one.setStatus(0);
            one.setCreateAt(now);
            one.setUpdateAt(now);
            dbSmsPhoneCodeService.save(one);
        }

        sendSmsService.sentToPhone("注册验证码为：" + code + "，请在1分钟内使用。", phone);
    }

    @Override
    public void validateRegisterCode(String phone, String code, int type) {
        Optional<DbSmsPhoneCode> oneOpt = dbSmsPhoneCodeService.lambdaQuery()
                .eq(DbSmsPhoneCode::getPhone, phone)
                .eq(DbSmsPhoneCode::getCode, code)
                .eq(DbSmsPhoneCode::getType, type)
                .oneOpt();

        if (oneOpt.isPresent()) {
            DbSmsPhoneCode one = oneOpt.get();
            LocalDateTime now = LocalDateTime.now();
            Integer expiration = smsProperties.getExpiration();

            // 存在+未使用+未过期
            if (one.getStatus() == 0
                    && one.getUpdateAt().plusSeconds(expiration).isAfter(now)
            ) {
                one.setStatus(1);   // 标记为已被使用
                dbSmsPhoneCodeService.updateById(one);
                return;
            }
        }

        throw new RuntimeException("验证码错误");
    }

    //TODO 生成信息验证码
    String generateCode() {
        return "test";
    }

}
