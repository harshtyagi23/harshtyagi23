package com.example.abstractfactory.impl;

import com.example.abstractfactory.api.MarketDashboardFactory;
import com.example.abstractfactory.api.QuoteWidget;
import com.example.abstractfactory.api.RiskWidget;

import java.util.Optional;

public final class EquityMarketDashboardFactory implements MarketDashboardFactory {
    @Override
    public QuoteWidget createQuoteWidget(String symbol, Optional<Double> lastPrice) {
        return new EquityQuoteWidget(symbol, lastPrice);
    }

    @Override
    public RiskWidget createRiskWidget(Optional<Integer> riskScore) {
        return new EquityRiskWidget(riskScore);
    }

    @Override
    public String marketFamily() {
        return "equity";
    }
}
