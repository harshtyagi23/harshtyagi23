package com.example.composite.impl;

import com.example.composite.api.PortfolioComponent;

public record CashPosition(String name, double balanceCurrency) implements PortfolioComponent {
    @Override
    public double marketValue() {
        return balanceCurrency;
    }

    @Override
    public String describe() {
        return name + " cash balance " + String.format("%.2f", balanceCurrency);
    }
}
