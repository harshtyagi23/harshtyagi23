package com.example.adapter;

import com.example.adapter.application.PortfolioService;
import com.example.adapter.impl.LegacyMarketFeed;
import com.example.adapter.impl.MarketFeedAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdapterPatternTest {

    @Test
    void shouldAdaptLegacyFeedIntoModernPortfolioApi() {
        PortfolioService service = new PortfolioService(new MarketFeedAdapter(new LegacyMarketFeed()));

        String snapshot = service.summarize("msft");

        assertEquals("MSFT|ACTIVE|120.50", snapshot);
    }

    @Test
    void shouldHandleBlankSymbolsGracefully() {
        PortfolioService service = new PortfolioService(new MarketFeedAdapter(new LegacyMarketFeed()));

        String snapshot = service.summarize("   ");

        assertEquals("UNKNOWN|UNAVAILABLE|0.00", snapshot);
    }
}
