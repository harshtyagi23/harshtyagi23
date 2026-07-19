package com.example.mediator.api;

public interface TradeMediator {
    TradeDecision submitTrade(String desk, String symbol, double quantity, String side);
}
