package com.example.visitor.impl;

import com.example.visitor.api.PortfolioElement;
import com.example.visitor.api.PortfolioVisitor;

import java.util.List;
import java.util.Optional;

public record PortfolioGroup(String name, List<PortfolioElement> elements, Optional<String> strategy) implements PortfolioElement {
    @Override
    public <T> T accept(PortfolioVisitor<T> visitor) {
        return visitor.visitPortfolioGroup(this);
    }
}
