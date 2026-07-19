package com.example.abstractfactory;

import com.example.abstractfactory.api.AssetClass;
import com.example.abstractfactory.api.MarketDashboardFactory;
import com.example.abstractfactory.api.MarketDashboardFactoryProvider;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class MarketDashboardFactoryTest {
    @Test
    void createsEquityFamilyProducts() {
        MarketDashboardFactory factory = MarketDashboardFactoryProvider.factoryFor(AssetClass.EQUITY);

        var dashboard = factory.createDashboard("ACME", Optional.of(128.75), Optional.of(4));

        assertInstanceOf(com.example.abstractfactory.impl.EquityQuoteWidget.class, dashboard.quoteWidget());
        assertInstanceOf(com.example.abstractfactory.impl.EquityRiskWidget.class, dashboard.riskWidget());
        assertEquals("ACME equity quote $128.75 | equity risk score 4", dashboard.render());
    }

    @Test
    void createsBondFamilyProducts() {
        MarketDashboardFactory factory = MarketDashboardFactoryProvider.factoryFor(AssetClass.BOND);

        var dashboard = factory.createDashboard("TREASURY", Optional.empty(), Optional.empty());

        assertInstanceOf(com.example.abstractfactory.impl.BondQuoteWidget.class, dashboard.quoteWidget());
        assertInstanceOf(com.example.abstractfactory.impl.BondRiskWidget.class, dashboard.riskWidget());
        assertEquals("TREASURY bond quote price unavailable | bond risk not rated", dashboard.render());
    }
}
