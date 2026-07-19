package com.example.composite;

import com.example.composite.api.PortfolioComponent;
import com.example.composite.impl.CashPosition;
import com.example.composite.impl.Holding;
import com.example.composite.impl.PortfolioGroup;
import com.example.composite.impl.PortfolioSummary;

import java.util.List;
import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        PortfolioComponent equity = new Holding("Growth Equity", "ACME", 120, 8.5, Optional.of("Technology"));
        PortfolioComponent cash = new CashPosition("Reserve Cash", 2500.0);
        PortfolioGroup portfolio = new PortfolioGroup("Core Portfolio", List.of(equity, cash), Optional.of("balanced"));

        PortfolioSummary summary = new PortfolioSummary(portfolio.name(), List.of(portfolio, equity, cash));
        System.out.println(portfolio.describe());
        System.out.println(summary.render());
    }
}
