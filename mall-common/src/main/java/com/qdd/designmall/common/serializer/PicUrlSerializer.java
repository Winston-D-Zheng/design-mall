package com.qdd.designmall.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.qdd.designmall.common.service.QiNiuYunService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PicUrlSerializer extends JsonSerializer<String> {
    private final QiNiuYunService qiNiuYunService;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        String[] r = qiNiuYunService.getUrls(value);
        gen.writeArray(r, 0, r.length);
    }
}
