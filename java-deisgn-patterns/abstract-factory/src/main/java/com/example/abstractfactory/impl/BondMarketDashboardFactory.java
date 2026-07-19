package com.example.abstractfactory.impl;

import com.example.abstractfactory.api.MarketDashboardFactory;
import com.example.abstractfactory.api.QuoteWidget;
import com.example.abstractfactory.api.RiskWidget;

import java.util.Optional;

public final class BondMarketDashboardFactory implements MarketDashboardFactory {
    @Override
    public QuoteWidget createQuoteWidget(String symbol, Optional<Double> lastPrice) {
        return new BondQuoteWidget(symbol, lastPrice);
    }

    @Override
    public RiskWidget createRiskWidget(Optional<Integer> riskScore) {
        return new BondRiskWidget(riskScore);
    }

    @Override
    public String marketFamily() {
        return "bond";
    }
}
