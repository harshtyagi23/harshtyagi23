package com.example.visitor;

import com.example.visitor.api.PortfolioElement;
import com.example.visitor.impl.CashPosition;
import com.example.visitor.impl.Holding;
import com.example.visitor.impl.PortfolioGroup;
import com.example.visitor.impl.ReportVisitor;
import com.example.visitor.impl.ValuationVisitor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VisitorPatternTest {
    @Test
    void calculatesPortfolioValueUsingVisitor() {
        PortfolioElement portfolio = new PortfolioGroup(
                "Core Portfolio",
                List.of(
                        new Holding("ACME", 100, 12.5, Optional.of("Technology")),
                        new CashPosition(500.0)
                ),
                Optional.of("balanced")
        );

        assertEquals(1750.0, portfolio.accept(new ValuationVisitor()));
        assertEquals("Core Portfolio strategy balanced value 1750.00", portfolio.accept(new ReportVisitor()));
    }
}
