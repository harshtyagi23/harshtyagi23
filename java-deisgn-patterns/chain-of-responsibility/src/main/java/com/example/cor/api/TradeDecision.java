package com.example.cor.api;

public record TradeDecision(boolean approved, String stage, String message) {
    public static TradeDecision approved(String stage, String message) {
        return new TradeDecision(true, stage, message);
    }

    public static TradeDecision rejected(String stage, String message) {
        return new TradeDecision(false, stage, message);
    }
}
