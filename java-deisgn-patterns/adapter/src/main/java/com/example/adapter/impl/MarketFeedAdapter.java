package com.example.adapter.impl;

import com.example.adapter.api.PortfolioApi;
import com.example.adapter.domain.PortfolioSnapshot;

import java.util.Optional;

public class MarketFeedAdapter implements PortfolioApi {
    private final LegacyMarketFeed legacyMarketFeed;

    public MarketFeedAdapter(LegacyMarketFeed legacyMarketFeed) {
        this.legacyMarketFeed = legacyMarketFeed;
    }

    @Override
    public String getPortfolioSnapshot(String symbol) {
        return Optional.ofNullable(symbol)
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .map(this::toSnapshot)
                .map(snapshot -> "%s|%s|%.2f".formatted(snapshot.symbol(), snapshot.status(), snapshot.value()))
                .orElse("UNKNOWN|UNAVAILABLE|0.00");
    }

    private PortfolioSnapshot toSnapshot(String symbol) {
        String quote = legacyMarketFeed.fetchQuote(symbol);
        String[] parts = quote.split(":");
        String ticker = parts[0];
        double price = Double.parseDouble(parts[1]);
        String status = switch (ticker.toLowerCase()) {
            case "msft" -> "ACTIVE";
            case "aapl" -> "ACTIVE";
            default -> "UNKNOWN";
        };
        return new PortfolioSnapshot(symbol.toUpperCase(), status, price);
    }
}
