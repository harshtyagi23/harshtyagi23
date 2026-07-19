package com.example.facade.api;

public record TradeConfirmation(String symbol, double quantity, double executedPrice, String route) {
    public String render() {
        return symbol + " " + quantity + " @ " + String.format("%.2f", executedPrice) + " via " + route;
    }
}
