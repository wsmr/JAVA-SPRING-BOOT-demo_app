package com.example.demo.pack1;

public enum ActTypes {
    WITHDRAWAL("W"),
    SAVING("S"),
    FIX("F"),
    LOAN("L");

    private final String code;

    ActTypes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
