package com.example.visitor.impl;

import com.example.visitor.api.PortfolioElement;
import com.example.visitor.api.PortfolioVisitor;

public record CashPosition(double balance) implements PortfolioElement {
    @Override
    public <T> T accept(PortfolioVisitor<T> visitor) {
        return visitor.visitCash(this);
    }
}
