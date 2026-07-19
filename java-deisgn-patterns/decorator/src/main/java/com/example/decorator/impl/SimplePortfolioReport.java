package com.example.decorator.impl;

import com.example.decorator.api.PortfolioReport;
import com.example.decorator.domain.Portfolio;

public class SimplePortfolioReport implements PortfolioReport {
    private final Portfolio portfolio;

    public SimplePortfolioReport(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Override
    public String render() {
        return "Portfolio " + portfolio.name() + " value " + portfolio.value().setScale(2);
    }
}
