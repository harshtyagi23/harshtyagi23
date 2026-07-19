package com.example.decorator;

import com.example.decorator.api.PortfolioReport;
import com.example.decorator.domain.Portfolio;
import com.example.decorator.impl.CostsDecorator;
import com.example.decorator.impl.GrowthDecorator;
import com.example.decorator.impl.SimplePortfolioReport;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DecoratorPatternTest {

    @Test
    void shouldDecorateReportWithAdditionalInsights() {
        Portfolio portfolio = new Portfolio("Tech Growth", BigDecimal.valueOf(125000));
        PortfolioReport report = new CostsDecorator(new GrowthDecorator(new SimplePortfolioReport(portfolio)));

        String output = report.render();

        assertEquals("Portfolio Tech Growth value 125000.00 with growth outlook and cost analysis", output);
    }
}
