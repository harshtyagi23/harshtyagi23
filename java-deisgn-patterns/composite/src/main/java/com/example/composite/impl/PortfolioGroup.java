package com.example.composite.impl;

import com.example.composite.api.PortfolioComponent;
import com.example.composite.api.PortfolioNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class PortfolioGroup implements PortfolioNode {
    private final String name;
    private final List<PortfolioComponent> children;
    private final Optional<String> strategy;

    public PortfolioGroup(String name, List<PortfolioComponent> children, Optional<String> strategy) {
        this.name = name;
        this.children = List.copyOf(children);
        this.strategy = strategy;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public double marketValue() {
        return children.stream().mapToDouble(PortfolioComponent::marketValue).sum();
    }

    @Override
    public String describe() {
        return name + " group " + strategy.map(value -> "strategy " + value).orElse("strategy unspecified") + " total " + String.format("%.2f", marketValue());
    }

    @Override
    public List<PortfolioComponent> children() {
        return Collections.unmodifiableList(children);
    }

    public PortfolioGroup add(PortfolioComponent component) {
        List<PortfolioComponent> updatedChildren = new ArrayList<>(children);
        updatedChildren.add(component);
        return new PortfolioGroup(name, updatedChildren, strategy);
    }
}
