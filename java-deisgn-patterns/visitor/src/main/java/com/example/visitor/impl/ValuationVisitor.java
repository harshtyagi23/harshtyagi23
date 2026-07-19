package com.example.visitor.impl;

import com.example.visitor.api.PortfolioVisitor;

public final class ValuationVisitor implements PortfolioVisitor<Double> {
    @Override
    public Double visitHolding(Holding holding) {
        return holding.marketValue();
    }

    @Override
    public Double visitCash(CashPosition cashPosition) {
        return cashPosition.balance();
    }

    @Override
    public Double visitPortfolioGroup(PortfolioGroup portfolioGroup) {
        return portfolioGroup.elements().stream()
                .mapToDouble(element -> element.accept(this))
                .sum();
    }
}
