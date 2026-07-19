package com.example.facade.impl;

public final class ExecutionService {
    public double execute(String symbol, double quantity, double price) {
        double slippage = quantity > 500.0 ? 0.05 : 0.01;
        return price + slippage;
    }
}
