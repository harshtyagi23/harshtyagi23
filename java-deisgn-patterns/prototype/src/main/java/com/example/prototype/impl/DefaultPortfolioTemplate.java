package com.example.prototype.impl;

import com.example.prototype.api.PortfolioTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class DefaultPortfolioTemplate implements PortfolioTemplate {
    private final String name;
    private final String riskLevel;
    private final List<String> holdings;
    private Optional<String> benchmark;

    public DefaultPortfolioTemplate(String name, String riskLevel, List<String> holdings, Optional<String> benchmark) {
        this.name = name;
        this.riskLevel = riskLevel;
        this.holdings = new ArrayList<>(holdings);
        this.benchmark = benchmark == null ? Optional.empty() : benchmark;
    }

    private DefaultPortfolioTemplate(DefaultPortfolioTemplate source) {
        this(source.name, source.riskLevel, source.holdings, source.benchmark);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String riskLevel() {
        return riskLevel;
    }

    @Override
    public List<String> holdings() {
        return List.copyOf(holdings);
    }

    @Override
    public Optional<String> benchmark() {
        return benchmark;
    }

    @Override
    public void addHolding(String holding) {
        holdings.add(holding);
    }

    @Override
    public void setBenchmark(String benchmark) {
        this.benchmark = Optional.ofNullable(benchmark);
    }

    @Override
    public PortfolioTemplate copy() {
        return new DefaultPortfolioTemplate(this);
    }

    public String render() {
        return name + " " + riskLevel + " holdings=" + holdings + " benchmark=" + benchmark.orElse("none");
    }
}
