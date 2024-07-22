package com.qdd.designmall.common.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qdd.designmall.common.config.WeixinProperties;
import com.qdd.designmall.common.service.WeiXinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeiXinServiceImpl implements WeiXinService {
    private final WeixinProperties properties;
    private final ObjectMapper mapper;

    private String accessToken;
    private LocalDateTime accessTokenExpiredTime;    // 上次更新时间

    private static RestClient weixinClient() {
        return RestClient.builder()
                .requestFactory(
                        new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
                )
                .baseUrl("https://api.weixin.qq.com").build();
    }


    @Override
    public String generateMiniProgramQRCode(String payload) {
        updateAccessToken();

        RestClient restClient = weixinClient();
        byte[] buffer = restClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/wxa/getwxacodeunlimit")
                        .queryParam("access_token", accessToken)
                        .build()
                )
                .body(Map.of("scene", payload))
                .exchange((request, response) -> {
                    MediaType contentType = response.getHeaders().getContentType();
                    // 返回类型不是图片
                    if (contentType == null
                            || !contentType.isCompatibleWith(MediaType.IMAGE_PNG)
                            && !contentType.isCompatibleWith(MediaType.IMAGE_JPEG)
                    ) {
                        log.error(response.bodyTo(String.class));
                        throw new RuntimeException("微信小程序二维码获取失败");
                    }
                    return Objects.requireNonNull(response.bodyTo(byte[].class));
                });

        return Base64.getEncoder().encodeToString(buffer);
    }


    /**
     * 更新AccessToken
     * 在需要时必须先调用该方法确保有效！！！
     */
    private void updateAccessToken() {
        if (accessToken != null
                && accessTokenExpiredTime.isAfter(LocalDateTime.now())) {
            return;
        }

        RestClient restClient = weixinClient();
        String body = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cgi-bin/token")
                        .queryParam("grant_type", "client_credential")
                        .queryParam("appid", properties.getAppid())
                        .queryParam("secret", properties.getSecret())
                        .build()
                ).retrieve()
                .body(String.class);

        try {
            Map map = mapper.readValue(body, Map.class);
            if (map.get("errcode") != null) {
                log.error(mapper.writeValueAsString(map));
                throw new RuntimeException("获取微信小程序access_token失败");
            }
            String token = mapper.writeValueAsString(map.get("access_token"));
            token = token.replaceAll("\"", "");     // 去掉多余的 "
            accessToken = token;
            accessTokenExpiredTime = LocalDateTime.now().plusSeconds(6500);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
