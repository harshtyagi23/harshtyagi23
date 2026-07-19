package com.example.abstractfactory.api;

import java.util.Optional;

public interface MarketDashboardFactory {
    QuoteWidget createQuoteWidget(String symbol, Optional<Double> lastPrice);

    RiskWidget createRiskWidget(Optional<Integer> riskScore);

    String marketFamily();

    default MarketDashboard createDashboard(String symbol, Optional<Double> lastPrice, Optional<Integer> riskScore) {
        return new MarketDashboard(
                createQuoteWidget(symbol, lastPrice),
                createRiskWidget(riskScore)
        );
    }
}
