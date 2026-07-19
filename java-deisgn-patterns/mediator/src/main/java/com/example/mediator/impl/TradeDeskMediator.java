package com.example.mediator.impl;

import com.example.mediator.api.TradeDecision;
import com.example.mediator.api.TradeMediator;

import java.util.Optional;

public final class TradeDeskMediator implements TradeMediator {
    @Override
    public TradeDecision submitTrade(String desk, String symbol, double quantity, String side) {
        if (quantity <= 0) {
            return new TradeDecision(false, "quantity must be positive", desk);
        }
        if (symbol.isBlank()) {
            return new TradeDecision(false, "symbol missing", desk);
        }

        Optional<String> route = Optional.of(desk)
                .filter(value -> value.equalsIgnoreCase("equity") || value.equalsIgnoreCase("fixed-income"))
                .map(value -> value.toLowerCase() + "-routing");

        return route
                .map(value -> new TradeDecision(true, side + " " + symbol + " x " + quantity, value))
                .orElseGet(() -> new TradeDecision(false, "desk not supported", desk));
    }
}
