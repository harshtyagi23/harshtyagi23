package com.example.composite.impl;

import com.example.composite.api.PortfolioComponent;

import java.util.Optional;

public record Holding(String name, String ticker, double units, double pricePerUnit, Optional<String> sector) implements PortfolioComponent {
    @Override
    public double marketValue() {
        return units * pricePerUnit;
    }

    @Override
    public String describe() {
        return name + " (" + ticker + ") " + sector.map(value -> value + " sector").orElse("sector unknown") + " value " + String.format("%.2f", marketValue());
    }
}
