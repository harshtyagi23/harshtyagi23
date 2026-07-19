package com.example.facade.impl;

public final class RiskService {
    public boolean approve(String symbol, double quantity) {
        return quantity <= 1000.0 && !symbol.isBlank();
    }
}
