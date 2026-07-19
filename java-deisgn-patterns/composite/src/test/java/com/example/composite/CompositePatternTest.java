package com.example.composite;

import com.example.composite.api.PortfolioComponent;
import com.example.composite.impl.CashPosition;
import com.example.composite.impl.Holding;
import com.example.composite.impl.PortfolioGroup;
import com.example.composite.impl.PortfolioSummary;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositePatternTest {
    @Test
    void groupsAggregateLeafValues() {
        PortfolioComponent equity = new Holding("Growth Equity", "ACME", 120, 8.5, Optional.of("Technology"));
        PortfolioComponent cash = new CashPosition("Reserve Cash", 2500.0);
        PortfolioGroup portfolio = new PortfolioGroup("Core Portfolio", List.of(equity, cash), Optional.of("balanced"));

        assertFalse(equity instanceof PortfolioGroup);
        assertEquals(1020.0, equity.marketValue());
        assertEquals(2500.0, cash.marketValue());
        assertFalse(portfolio.isLeaf());
        assertEquals(3520.0, portfolio.marketValue());
        assertEquals("Core Portfolio group strategy balanced total 3520.00", portfolio.describe());
    }

    @Test
    void summaryRendersTotalAcrossComponents() {
        PortfolioComponent equity = new Holding("Income Equity", "BLUE", 50, 11.0, Optional.empty());
        PortfolioComponent cash = new CashPosition("Reserve Cash", 500.0);
        PortfolioSummary summary = new PortfolioSummary("Income Sleeve", List.of(equity, cash));

        assertEquals("Income Sleeve summary total 1050.00", summary.render());
        assertEquals(1050.0, summary.totalValue());
    }
}
