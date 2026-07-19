package com.example.builder.api;

import com.example.builder.domain.Portfolio;
import java.util.Optional;
import java.util.function.Consumer;

public interface PortfolioBuilder {
    PortfolioBuilder withName(String name);
    PortfolioBuilder withRiskLevel(String riskLevel);
    PortfolioBuilder withAllocation(double allocation);
    PortfolioBuilder withBenchmark(String benchmark);

    default PortfolioBuilder apply(Consumer<PortfolioBuilder> configurer) {
        configurer.accept(this);
        return this;
    }

    Portfolio build();

    static PortfolioBuilder builder() {
        return new PortfolioBuilderImpl();
    }
}

final class PortfolioBuilderImpl implements PortfolioBuilder {
    private String name;
    private String riskLevel;
    private double allocation;
    private String benchmark;

    @Override
    public PortfolioBuilder withName(String name) {
        this.name = Optional.ofNullable(name).orElse("Unnamed Portfolio");
        return this;
    }

    @Override
    public PortfolioBuilder withRiskLevel(String riskLevel) {
        this.riskLevel = Optional.ofNullable(riskLevel).orElse("Moderate");
        return this;
    }

    @Override
    public PortfolioBuilder withAllocation(double allocation) {
        this.allocation = allocation;
        return this;
    }

    @Override
    public PortfolioBuilder withBenchmark(String benchmark) {
        this.benchmark = Optional.ofNullable(benchmark).orElse("S&P 500");
        return this;
    }

    @Override
    public Portfolio build() {
        return new Portfolio(name, riskLevel, allocation, benchmark);
    }
}
