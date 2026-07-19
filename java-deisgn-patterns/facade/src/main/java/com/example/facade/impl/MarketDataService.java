package com.example.facade.impl;

import java.util.Optional;

public final class MarketDataService {
    public Optional<Double> lookupPrice(String symbol) {
        return switch (symbol.toUpperCase()) {
            case "ACME" -> Optional.of(128.75);
            case "BLUE" -> Optional.of(11.20);
            default -> Optional.empty();
        };
    }
}
