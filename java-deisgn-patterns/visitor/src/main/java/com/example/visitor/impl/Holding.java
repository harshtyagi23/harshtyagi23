package com.example.visitor.impl;

import com.example.visitor.api.PortfolioElement;
import com.example.visitor.api.PortfolioVisitor;

import java.util.Optional;

public record Holding(String symbol, double units, double pricePerUnit, Optional<String> sector) implements PortfolioElement {
    @Override
    public <T> T accept(PortfolioVisitor<T> visitor) {
        return visitor.visitHolding(this);
    }

    public double marketValue() {
        return units * pricePerUnit;
    }
}
