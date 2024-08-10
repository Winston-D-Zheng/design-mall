package com.qdd.designmall.mallexternal.service.impl;

import com.qdd.designmall.mallexternal.config.SmsProperties;
import com.qdd.designmall.mallexternal.config.TencentSmsProperties;
import com.qdd.designmall.mallexternal.service.SendSmsService;
import com.tencentcloudapi.common.AbstractModel;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TencentSendSmsServiceImpl implements SendSmsService {
    private final SmsProperties smsProperties;
    private final TencentSmsProperties tencentSmsProperties;


    @Override
    public void sentToPhone(String content, String... phones) {
        String SecretId = tencentSmsProperties.getSecretId();
        String SecretKey = tencentSmsProperties.getSecretKey();

        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-beijing", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(phones);

            req.setSmsSdkAppId("1400927373");
            req.setTemplateId("2228976");
            req.setSignName("万帮科技门户网站");

            String[] templateParamSet1 = {content, String.valueOf((smsProperties.getExpiration() / 60))};
            req.setTemplateParamSet(templateParamSet1);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            log.info("发送短信成功，回显信息为{}", AbstractModel.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            log.error("发送短信失败，错误信息为{}", e.toString());
            throw new RuntimeException("发送短信失败，错误信息为" + e.toString());
        }
    }
}
