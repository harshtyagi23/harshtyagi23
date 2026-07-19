package com.example.adapter.impl;

public class LegacyMarketFeed {
    public String fetchQuote(String symbol) {
        return switch (symbol.toLowerCase()) {
            case "msft" -> "MSFT:120.50";
            case "aapl" -> "AAPL:190.25";
            default -> "UNKNOWN:0.00";
        };
    }
}
