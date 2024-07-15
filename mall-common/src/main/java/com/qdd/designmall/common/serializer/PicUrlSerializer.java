package com.qdd.designmall.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.qdd.designmall.common.service.QiNiuYunService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class PicUrlSerializer extends JsonSerializer<String> {
    private final QiNiuYunService qiNiuYunService;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        // 使用虚拟线程的线程池
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        try(executor){
            Object[] r = Arrays.stream(value.split(","))
                    .map(v -> executor.submit(() -> Map.of("key", v, "url", qiNiuYunService.getUrl(v))))
                    .map(f -> {
                        try {
                            return f.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray();
            gen.writeObject(r);
        }
    }
}
