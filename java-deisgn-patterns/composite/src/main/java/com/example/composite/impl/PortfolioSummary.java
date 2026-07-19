package com.example.composite.impl;

import com.example.composite.api.PortfolioComponent;

import java.util.List;

public record PortfolioSummary(String name, List<PortfolioComponent> components) {
    public double totalValue() {
        return components.stream().mapToDouble(PortfolioComponent::marketValue).sum();
    }

    public String render() {
        return name + " summary total " + String.format("%.2f", totalValue());
    }
}
