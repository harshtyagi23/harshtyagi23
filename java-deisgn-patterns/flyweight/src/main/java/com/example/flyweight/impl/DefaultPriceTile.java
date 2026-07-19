package com.example.flyweight.impl;

import com.example.flyweight.api.PriceTile;

public record DefaultPriceTile(String symbol) implements PriceTile {
    @Override
    public String render(double marketPrice, String venue) {
        return symbol + " @ " + String.format("%.2f", marketPrice) + " on " + venue;
    }
}
