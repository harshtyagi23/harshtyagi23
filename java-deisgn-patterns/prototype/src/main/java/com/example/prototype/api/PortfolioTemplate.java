package com.example.prototype.api;

import java.util.List;
import java.util.Optional;

public interface PortfolioTemplate extends Prototype<PortfolioTemplate> {
    String name();

    String riskLevel();

    List<String> holdings();

    Optional<String> benchmark();

    void addHolding(String holding);

    void setBenchmark(String benchmark);
}
