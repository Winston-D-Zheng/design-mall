package com.qdd.designmall.common.enums;

import java.util.stream.Stream;

public enum CmsReadState {
    UNREAD(0), READ(1);
    private Integer value;

    CmsReadState(Integer value) {
        this.value = value;
    }

    public CmsReadState getEnum(Integer num) {
        return Stream.of(values())
                .filter(e -> e.value.equals(num)).findFirst()
                .orElseThrow(() -> new RuntimeException("枚举类型CmsReadState value=" + num + "不存在"));
    }

    public Integer value() {
        return value;
    }
}
