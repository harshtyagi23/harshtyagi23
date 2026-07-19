package com.example.facade;

import com.example.facade.impl.DefaultTradeFacade;
import com.example.facade.impl.ExecutionService;
import com.example.facade.impl.MarketDataService;
import com.example.facade.impl.RiskService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FacadePatternTest {
    @Test
    void executesMarketTradeThroughSingleFacade() {
        DefaultTradeFacade facade = new DefaultTradeFacade(new MarketDataService(), new RiskService(), new ExecutionService());

        var confirmation = facade.executeTrade("ACME", 200, Optional.empty());

        assertEquals("ACME 200.0 @ 128.76 via market", confirmation.render());
    }

    @Test
    void usesProvidedLimitPriceWhenPresent() {
        DefaultTradeFacade facade = new DefaultTradeFacade(new MarketDataService(), new RiskService(), new ExecutionService());

        var confirmation = facade.executeTrade("BLUE", 25, Optional.of(12.0));

        assertEquals("BLUE 25.0 @ 12.01 via limit", confirmation.render());
    }

    @Test
    void rejectsOversizedTrades() {
        DefaultTradeFacade facade = new DefaultTradeFacade(new MarketDataService(), new RiskService(), new ExecutionService());

        assertThrows(IllegalStateException.class, () -> facade.executeTrade("ACME", 5000, Optional.empty()));
    }
}
