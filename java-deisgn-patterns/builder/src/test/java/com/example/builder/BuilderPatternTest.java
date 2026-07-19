package com.example.builder;

import com.example.builder.application.PortfolioService;
import com.example.builder.domain.Portfolio;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BuilderPatternTest {

    @Test
    void shouldBuildPortfolioWithFluentBuilderAndModernJavaFeatures() {
        PortfolioService service = PortfolioService.createDefault();

        Portfolio portfolio = service.createPortfolio("Growth Fund", "Aggressive", 0.75, "NASDAQ 100");
        Optional<String> description = service.describePortfolio(portfolio);

        assertEquals("Growth Fund", portfolio.name());
        assertEquals("Aggressive", portfolio.riskLevel());
        assertEquals(0.75, portfolio.allocation());
        assertEquals("NASDAQ 100", portfolio.benchmark());
        assertTrue(description.isPresent());
        assertEquals("High-growth portfolio", description.get());
    }
}
