package com.example.strategy.impl;

import com.example.strategy.api.OrderStrategy;

public class LimitOrderStrategy implements OrderStrategy {
    @Override
    public String execute(double price, int shares) {
        return "Executed limit order for " + price + " shares " + shares;
    }
}
