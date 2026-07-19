package com.example.abstractfactory.impl;

import com.example.abstractfactory.api.QuoteWidget;

import java.util.Optional;

public record BondQuoteWidget(String symbol, Optional<Double> lastPrice) implements QuoteWidget {
    @Override
    public String marketFamily() {
        return "bond";
    }

    @Override
    public String render() {
        return symbol + " bond quote " + lastPrice.map(price -> "$" + String.format("%.2f", price)).orElse("price unavailable");
    }
}
