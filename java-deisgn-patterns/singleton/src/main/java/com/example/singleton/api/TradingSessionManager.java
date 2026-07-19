package com.example.singleton.api;

import java.util.Optional;

public final class TradingSessionManager {
    private static volatile TradingSessionManager instance;
    private String currentSessionId;

    private TradingSessionManager() {
        this.currentSessionId = "SESSION-001";
    }

    public static TradingSessionManager getInstance() {
        if (instance == null) {
            synchronized (TradingSessionManager.class) {
                if (instance == null) {
                    instance = new TradingSessionManager();
                }
            }
        }
        return instance;
    }

    public String getCurrentSessionId() {
        return currentSessionId;
    }

    public void setCurrentSessionId(String currentSessionId) {
        this.currentSessionId = Optional.ofNullable(currentSessionId)
                .filter(value -> !value.isBlank())
                .orElse("SESSION-001");
    }
}
