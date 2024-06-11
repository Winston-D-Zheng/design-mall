package com.qdd.designmall.malloms.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class OrderSnGenUtil {
    // 订单号前缀，可以根据业务需求自定义
    private static final String PREFIX = "ODR";
    // 用于生成序列号，保证订单号的唯一性
    private static final AtomicInteger sequence = new AtomicInteger(0);

    /**
     * 生成订单号。
     * @return 生成的订单号。
     */
    public static String generate() {
        // 获取当前时间的年月日小时分钟秒，用于订单号的有序性
        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                .format(Instant.now().atZone(ZoneId.systemDefault()));
        // 生成序列号，每次调用递增
        int currentSequence = sequence.incrementAndGet();
        // 将各部分拼接成最终的订单号
        return PREFIX + timestamp + String.format("%04d", currentSequence);
    }

    public static void main(String[] args) {
        // 测试生成订单号
        for (int i = 0; i < 5; i++) {
            System.out.println(generate());
        }
    }
}
