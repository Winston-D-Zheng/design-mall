package com.qdd.designmall.mallchat.enums;

import java.util.stream.Stream;

public enum EMsgType {

    TEXT(1),
    IMAGE(2);

    private final Integer value;

    EMsgType(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public static EMsgType of(Integer value) {
        return Stream.of(EMsgType.values())
                .filter(e -> e.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("枚举类型EMsgType value=" + value + "不存在"));
    }
}
