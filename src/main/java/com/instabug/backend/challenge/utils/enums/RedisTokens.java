package com.instabug.backend.challenge.utils.enums;

public enum RedisTokens {

    COUNT("count");

    private final String tokenKey;

    RedisTokens(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    @Override
    public String toString() {
        return tokenKey;
    }
}