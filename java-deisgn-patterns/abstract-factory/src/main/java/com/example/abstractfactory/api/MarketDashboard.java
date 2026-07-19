package com.example.abstractfactory.api;

public record MarketDashboard(QuoteWidget quoteWidget, RiskWidget riskWidget) {
    public String render() {
        return quoteWidget.render() + " | " + riskWidget.render();
    }
}
