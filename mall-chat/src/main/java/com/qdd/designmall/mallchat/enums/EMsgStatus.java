package com.qdd.designmall.mallchat.enums;

import java.util.stream.Stream;

public enum EMsgStatus {

    UNREAD(0),
    READ(1),
    DELETED(2),
    SEND(3);

    private final Integer value;

    EMsgStatus(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public static EMsgStatus of(Integer value) {
        return Stream.of(EMsgStatus.values())
                .filter(e -> e.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("枚举类型EMsgStatus value=" + value + "不存在"));
    }
}
