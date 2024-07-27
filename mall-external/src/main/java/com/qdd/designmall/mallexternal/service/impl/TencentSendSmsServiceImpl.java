package com.qdd.designmall.mallexternal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdd.designmall.mallexternal.config.TencentSmsProperties;
import com.qdd.designmall.mallexternal.service.SendSmsService;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
@RequiredArgsConstructor
@Slf4j
public class TencentSendSmsServiceImpl implements SendSmsService {
    private final TencentSmsProperties tencentSmsProperties;
    private final ObjectMapper mapper;
    private static final Charset charset = StandardCharsets.UTF_8;


    @Override
    public void sentToPhone(String content, String... phones) {

        // 实现腾讯短信服务功能
        String SECRET_ID = tencentSmsProperties.getSecretId();
        String secretKey = tencentSmsProperties.getSecretKey();
        String CT_JSON = "application/json; charset=utf-8";

        String service = "sms";
        String host = "sms.tencentcloudapi.com";
        String region = "ap-guangzhou";
        String action = "SendSms";
        String version = "2021-01-11";
        String algorithm = "TC3-HMAC-SHA256";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 注意时区，否则容易出错
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String date = sdf.format(new Date(Long.parseLong(timestamp + "000")));

        // ************* 步骤 1：拼接规范请求串 *************
        String httpRequestMethod = "POST";
        String canonicalUri = "/";
        String canonicalQueryString = "";
        String canonicalHeaders = "content-type:application/json; charset=utf-8\n" + "host:" + host + "\n";
        String signedHeaders = "content-type;host";

        HashMap<String, Object> params = new HashMap<>();
        // 实际调用需要更新参数，这里仅作为演示签名验证通过的例子
        params.put("SmsSdkAppId", "1400006666");
        params.put("SignName", "腾讯云");
        params.put("TemplateId", "1234");
        params.put("TemplateParamSet", new String[]{"12345"});
        params.put("PhoneNumberSet", phones);
        params.put("SessionContext", "test");
        String payload = null;
        try {
            payload = mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // String payload = "{\"Limit\": 1, \"Filters\": [{\"Values\": [\"\\u672a\\u547d\\u540d\"], \"Name\": \"instance-name\"}]}";
        String hashedRequestPayload = sha256Hex(payload);
        String canonicalRequest = httpRequestMethod + "\n" + canonicalUri + "\n" + canonicalQueryString + "\n"
                + canonicalHeaders + "\n" + signedHeaders + "\n" + hashedRequestPayload;
        System.out.println(canonicalRequest);

        // ************* 步骤 2：拼接待签名字符串 *************
        String credentialScope = date + "/" + service + "/" + "tc3_request";
        String hashedCanonicalRequest = sha256Hex(canonicalRequest);
        String stringToSign = algorithm + "\n" + timestamp + "\n" + credentialScope + "\n" + hashedCanonicalRequest;
        System.out.println(stringToSign);

        // ************* 步骤 3：计算签名 *************
        byte[] secretDate = hmac256(("TC3" + SECRET_KEY).getBytes(charset), date);
        byte[] secretService = hmac256(secretDate, service);
        byte[] secretSigning = hmac256(secretService, "tc3_request");
        String signature = DatatypeConverter.printHexBinary(hmac256(secretSigning, stringToSign)).toLowerCase();
        System.out.println(signature);

        // ************* 步骤 4：拼接 Authorization *************
        String authorization = algorithm + " " + "Credential=" + SECRET_ID + "/" + credentialScope + ", "
                + "SignedHeaders=" + signedHeaders + ", " + "Signature=" + signature;
        System.out.println(authorization);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        headers.put("Content-Type", CT_JSON);
        headers.put("Host", host);
        headers.put("X-TC-Action", action);
        headers.put("X-TC-Timestamp", timestamp);
        headers.put("X-TC-Version", version);
        headers.put("X-TC-Region", region);

        RestClient client = RestClient.create("https://" + host);
        String body = client.post()
                .headers(httpHeaders ->
                        headers.forEach(httpHeaders::add)
                )
                .body(payload)
                .retrieve()
                .body(String.class);
        log.warn(body);
    }

    public static byte[] hmac256(byte[] key, String msg) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, mac.getAlgorithm());
        try {
            mac.init(secretKeySpec);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return mac.doFinal(msg.getBytes(charset));
    }

    public static String sha256Hex(String s){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] d = md.digest(s.getBytes(charset));
        return DatatypeConverter.printHexBinary(d).toLowerCase();
    }
}
