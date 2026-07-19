package com.example.strategy.impl;

import com.example.strategy.api.OrderStrategy;

public class MarketOrderStrategy implements OrderStrategy {
    @Override
    public String execute(double price, int shares) {
        return "Executed market order for " + price + " shares " + shares;
    }
}
