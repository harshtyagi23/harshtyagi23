package com.example.facade.impl;

import com.example.facade.api.TradeConfirmation;
import com.example.facade.api.TradeFacade;

import java.util.Optional;

public final class DefaultTradeFacade implements TradeFacade {
    private final MarketDataService marketDataService;
    private final RiskService riskService;
    private final ExecutionService executionService;

    public DefaultTradeFacade(MarketDataService marketDataService, RiskService riskService, ExecutionService executionService) {
        this.marketDataService = marketDataService;
        this.riskService = riskService;
        this.executionService = executionService;
    }

    @Override
    public TradeConfirmation executeTrade(String symbol, double quantity, Optional<Double> limitPrice) {
        if (!riskService.approve(symbol, quantity)) {
            throw new IllegalStateException("Trade rejected by risk service");
        }

        double price = limitPrice.or(() -> marketDataService.lookupPrice(symbol))
                .orElseThrow(() -> new IllegalArgumentException("No price available for " + symbol));
        double executedPrice = executionService.execute(symbol, quantity, price);
        String route = limitPrice.isPresent() ? "limit" : "market";
        return new TradeConfirmation(symbol, quantity, executedPrice, route);
    }
}
