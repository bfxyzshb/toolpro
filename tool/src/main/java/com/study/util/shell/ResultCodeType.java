package com.study.util.shell;

public enum ResultCodeType {

    SUCCESS(1),
    ERROR(-1),
    NODATA(0);

    ResultCodeType(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ResultCodeType valueOf(int value) {
        for (ResultCodeType type : ResultCodeType.values()) {
            if (value == type.value) return type;
        }
        return null;
    }
}