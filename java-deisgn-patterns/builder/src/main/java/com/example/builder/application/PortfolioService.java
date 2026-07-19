package com.example.builder.application;

import com.example.builder.api.PortfolioBuilder;
import com.example.builder.domain.Portfolio;

import java.util.Optional;
import java.util.function.Supplier;

public class PortfolioService {
    public Portfolio createPortfolio(String name, String riskLevel, double allocation, String benchmark) {
        return PortfolioBuilder.builder()
                .withName(name)
                .withRiskLevel(riskLevel)
                .withAllocation(allocation)
                .withBenchmark(benchmark)
                .build();
    }

    public Optional<String> describePortfolio(Portfolio portfolio) {
        return Optional.ofNullable(portfolio)
                .map(Portfolio::summary);
    }

    public static PortfolioService createDefault() {
        Supplier<PortfolioService> supplier = PortfolioService::new;
        return supplier.get();
    }
}
