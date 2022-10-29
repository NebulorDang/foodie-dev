package com.imooc.enums;

/**
 * @Desc: 商品级别 枚举
 */

public enum Level {
    FIRST_LEVEL(1, "一级级类别"),
    SECOND_LEVEL(2, "一级类别"),
    THIRD_LEVEL(3, "三级类别");

    public final Integer type;

    public final String value;

    Level(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
