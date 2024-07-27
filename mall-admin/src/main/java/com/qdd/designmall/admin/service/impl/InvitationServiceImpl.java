package com.qdd.designmall.admin.service.impl;

import com.qdd.designmall.admin.service.InvitationService;
import com.qdd.designmall.mallexternal.service.WeiXinService;
import com.qdd.designmall.mbp.model.DbQrcodeValidation;
import com.qdd.designmall.mbp.model.DbShopUserRelation;
import com.qdd.designmall.mbp.service.DbQrcodeValidationService;
import com.qdd.designmall.mbp.service.DbShopUserRelationService;
import com.qdd.designmall.mbp.service.DbSmsShopService;
import com.qdd.designmall.security.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
    private final SecurityUserService securityUserService;
    private final DbSmsShopService dbSmsShopService;
    private final DbQrcodeValidationService dbQrcodeValidationService;
    private final DbShopUserRelationService dbShopUserRelationService;
    private final WeiXinService weiXinService;

    @Value("${z.domainUrl}")
    String domainUrl;


    @Override
    public String generateWriterInviteCode(Long shopId) {
        // 验证当前用户属于该店铺客服或店长
        Long userId = securityUserService.currentUserDetails().getUserId();
        boolean exists = dbSmsShopService.exists(shopId, userId);
        if (!exists) {
            boolean exists1 = dbShopUserRelationService.exists(shopId, userId, 1);
            if (!exists1) {
                throw new RuntimeException("您不是该店铺的店长或客服");
            }
        }

        // 二维码内容 behaviorCode_shopId_expireTime
        String content = String.format("%s_%d_%s", "1", shopId, LocalDateTime.now());

        // 生成二维码
        return weiXinService.generateMiniProgramQRCode(content);
    }

    @Override
    public void scanWriterInviteCode(Long shopId, LocalDateTime expireTime) {
        // ********* 验证 *********
        // 店铺存在
        dbSmsShopService.notExistsThrow(shopId);
        // 写手不在该店铺
        Long userId = securityUserService.currentUserDetails().getUserId();
        dbShopUserRelationService.existsThrow(shopId, userId, 2);
        // 二维码未逾期
        boolean after = expireTime.isAfter(LocalDateTime.now());
        if (!after) {
            throw new RuntimeException("二维码已过期");
        }


        // 将写手添加到店铺
        DbShopUserRelation entity = new DbShopUserRelation();
        entity.setCreateAt(LocalDateTime.now());
        entity.setUpdateAt(LocalDateTime.now());
        entity.setShopId(shopId);
        entity.setUserId(userId);
        entity.setRelation(2);      // 写手
        dbShopUserRelationService.save(entity);
    }


    @Override
    public String generateCustomerServiceInviteCode(Long shopId) {
        // ************ 校验 ************
        // 当前用户是该店店长
        Long userId = securityUserService.currentUserDetails().getUserId();
        dbSmsShopService.notExistsThrow(shopId, userId);


        // ************ 二维码内容 ************
        // 识别码
        String uniqueId = UUID.randomUUID().toString().replaceAll("_", "").substring(0, 3);
        // 超期时间
        LocalDateTime expiredTime = LocalDateTime.now().plusHours(24);
        // 二维码内容：behavior_shopId_expireTime_uuid
        String content = String.format("%s_%d_%s_%s",
                "2",
                shopId,
                expiredTime,
                uniqueId
        );

        // 生成二维码
        String rt = weiXinService.generateMiniProgramQRCode(content);

        // 保存到数据库，状态为未扫码
        DbQrcodeValidation entity = new DbQrcodeValidation();
        entity.setContent(content);
        entity.setCreateAt(LocalDateTime.now());
        dbQrcodeValidationService.save(entity);

        return rt;
    }

    @Override
    public void scanCustomerServiceInviteCode(Long shopId, LocalDateTime expiredTime, String uniqueId) {

        // ************ 校验 ************
        // 二维码未过期
        boolean after = expiredTime.isAfter(LocalDateTime.now());
        if (!after) {
            throw new RuntimeException("二维码已过期");
        }
        // 二维码未被扫描
        String content = String.format("%s_%d_%s_%s", "2", shopId, expiredTime, uniqueId);
        DbQrcodeValidation qr = dbQrcodeValidationService.nullableOne(content);
        if (qr == null) {
            throw new RuntimeException("二维码不合法");
        }
        if (qr.getScanStatus() == 1) {
            throw new RuntimeException("二维码已被使用");
        }
        // 店铺存在
        dbSmsShopService.notExistsThrow(shopId);
        // 客服不在该店铺
        Long userId = securityUserService.currentUserDetails().getUserId();
        dbShopUserRelationService.existsThrow(shopId, userId, 1);


        // 将二维码设置为已扫码
        qr.setScanStatus(1);
        dbQrcodeValidationService.updateById(qr);


        // 将客服加入店铺
        DbShopUserRelation entity = new DbShopUserRelation();
        entity.setCreateAt(LocalDateTime.now());
        entity.setUpdateAt(LocalDateTime.now());
        entity.setShopId(shopId);
        entity.setUserId(userId);
        entity.setRelation(1);      // 客服
        dbShopUserRelationService.save(entity);
    }
}
