package com.example.iterator;

import com.example.iterator.api.Trade;
import com.example.iterator.impl.ArrayTradeLedger;
import com.example.iterator.impl.TradeIteratorService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IteratorPatternTest {
    @Test
    void iteratesOverLedgerWithoutExposingInternalStructure() {
        ArrayTradeLedger ledger = new ArrayTradeLedger();
        ledger.add(new Trade("ACME", 100, "BUY", Optional.of("opening")));
        ledger.add(new Trade("BLUE", 50, "SELL", Optional.empty()));

        TradeIteratorService service = new TradeIteratorService(ledger);

        assertEquals(List.of(
                "ACME BUY 100.0 (opening)",
                "BLUE SELL 50.0"
        ), service.summarize());
        assertTrue(service.firstTrade().isPresent());
    }
}
