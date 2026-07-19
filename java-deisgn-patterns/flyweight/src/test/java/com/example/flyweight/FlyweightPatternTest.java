package com.example.flyweight;

import com.example.flyweight.api.PriceTileFactory;
import com.example.flyweight.impl.QuoteBoard;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class FlyweightPatternTest {
    @Test
    void reusesTilesForTheSameSymbol() {
        PriceTileFactory factory = new PriceTileFactory();

        var first = factory.getTile("ACME");
        var second = factory.getTile("ACME");

        assertSame(first, second);
        assertEquals(1, factory.cacheSize());
    }

    @Test
    void rendersQuotesUsingSharedTiles() {
        QuoteBoard board = new QuoteBoard(new PriceTileFactory());

        var rendered = board.renderQuotes(List.of(
                new QuoteBoard.QuoteRequest("ACME", 128.75, "NYSE", Optional.of("primary")),
                new QuoteBoard.QuoteRequest("ACME", 129.10, "NYSE", Optional.empty()),
                new QuoteBoard.QuoteRequest("BLUE", 11.20, "LSE", Optional.empty())
        ));

        assertEquals(List.of(
                "ACME @ 128.75 on NYSE",
                "ACME @ 129.10 on NYSE",
                "BLUE @ 11.20 on LSE"
        ), rendered);
    }
}
