package com.qdd.designmall.common.enums;


import java.util.stream.Stream;

public enum ENicknameStatus {

    ANONYMOUS(0), REAL_NAME(1);

    private Integer value;

    ENicknameStatus(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public ENicknameStatus of(Integer num) {
        return Stream.of(ENicknameStatus.values())
                .filter(e -> e.value.equals(num)).findFirst()
                .orElseThrow(() -> new RuntimeException("枚举类型ENicknameStatus value=" + num + "不存在"));
    }
}
