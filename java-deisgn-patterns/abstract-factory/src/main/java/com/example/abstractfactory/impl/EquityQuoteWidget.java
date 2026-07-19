package com.example.abstractfactory.impl;

import com.example.abstractfactory.api.QuoteWidget;

import java.util.Optional;

public record EquityQuoteWidget(String symbol, Optional<Double> lastPrice) implements QuoteWidget {
    @Override
    public String marketFamily() {
        return "equity";
    }

    @Override
    public String render() {
        return symbol + " equity quote " + lastPrice.map(price -> "$" + String.format("%.2f", price)).orElse("price unavailable");
    }
}
