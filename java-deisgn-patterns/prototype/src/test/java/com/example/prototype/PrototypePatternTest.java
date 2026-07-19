package com.example.prototype;

import com.example.prototype.impl.DefaultPortfolioTemplate;
import com.example.prototype.impl.PortfolioPrototypeRegistry;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrototypePatternTest {
    @Test
    void clonesPortfolioTemplatesWithoutSharingMutableState() {
        PortfolioPrototypeRegistry registry = new PortfolioPrototypeRegistry();
        DefaultPortfolioTemplate original = new DefaultPortfolioTemplate("Growth Fund", "Aggressive", List.of("ACME", "BLUE"), Optional.of("NASDAQ 100"));
        registry.register("growth", original);

        DefaultPortfolioTemplate clone = (DefaultPortfolioTemplate) registry.clone("growth");
        clone.addHolding("GREEN");
        clone.setBenchmark("MSCI World");

        assertNotSame(original, clone);
        assertEquals("Growth Fund Aggressive holdings=[ACME, BLUE] benchmark=NASDAQ 100", original.render());
        assertEquals("Growth Fund Aggressive holdings=[ACME, BLUE, GREEN] benchmark=MSCI World", clone.render());
    }

    @Test
    void rejectsUnknownPrototypeKeys() {
        PortfolioPrototypeRegistry registry = new PortfolioPrototypeRegistry();

        assertThrows(IllegalArgumentException.class, () -> registry.clone("missing"));
    }
}
