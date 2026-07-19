package com.example.visitor.api;

public interface PortfolioElement {
    <T> T accept(PortfolioVisitor<T> visitor);
}
