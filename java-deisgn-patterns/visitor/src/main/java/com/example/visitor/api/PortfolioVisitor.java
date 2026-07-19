package com.example.visitor.api;

public interface PortfolioVisitor<T> {
    T visitHolding(com.example.visitor.impl.Holding holding);

    T visitCash(com.example.visitor.impl.CashPosition cashPosition);

    T visitPortfolioGroup(com.example.visitor.impl.PortfolioGroup portfolioGroup);
}
