package com.example.visitor.impl;

import com.example.visitor.api.PortfolioVisitor;

public final class ReportVisitor implements PortfolioVisitor<String> {
    @Override
    public String visitHolding(Holding holding) {
        return "Holding " + holding.symbol() + " value " + String.format("%.2f", holding.marketValue());
    }

    @Override
    public String visitCash(CashPosition cashPosition) {
        return "Cash " + String.format("%.2f", cashPosition.balance());
    }

    @Override
    public String visitPortfolioGroup(PortfolioGroup portfolioGroup) {
        return portfolioGroup.name() + " strategy " + portfolioGroup.strategy().orElse("unspecified") + " value " + String.format("%.2f", new ValuationVisitor().visitPortfolioGroup(portfolioGroup));
    }
}
