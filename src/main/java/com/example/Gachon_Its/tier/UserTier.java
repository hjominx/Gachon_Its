package com.example.Gachon_Its.tier;

import lombok.Getter;
@Getter
public enum UserTier {
    BASIC("Basic"),         // 0~100점
    PREMIUM("Premium"),     // 101~300점
    VIP("VIP");             // 301~

    private final String displayName;
    UserTier(String displayName) {
        this.displayName = displayName;
    }

}
