package com.example.adapter.application;

import com.example.adapter.api.PortfolioApi;

import java.util.Optional;

public class PortfolioService {
    private final PortfolioApi portfolioApi;

    public PortfolioService(PortfolioApi portfolioApi) {
        this.portfolioApi = portfolioApi;
    }

    public String summarize(String symbol) {
        return Optional.ofNullable(portfolioApi)
                .map(api -> api.getPortfolioSnapshot(symbol))
                .orElse("UNKNOWN|UNAVAILABLE|0.00");
    }
}
