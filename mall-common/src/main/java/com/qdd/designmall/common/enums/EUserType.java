package com.qdd.designmall.common.enums;

import java.util.stream.Stream;

public enum EUserType {
    ADMIN(0),
    MEMBER(1);

    private final Integer value;

    EUserType(Integer value) {
        this.value = value;
    }

    public static EUserType of(Integer num) {
        return Stream.of(values())
                .filter(e -> e.value.equals(num)).findFirst()
                .orElseThrow(() -> new RuntimeException("枚举类型ChatType value=" + num + "不存在"));
    }

    public Integer value() {
        return value;
    }

}
