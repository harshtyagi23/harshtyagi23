package com.example.iterator;

import com.example.iterator.api.Trade;
import com.example.iterator.impl.ArrayTradeLedger;
import com.example.iterator.impl.TradeIteratorService;

import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        ArrayTradeLedger ledger = new ArrayTradeLedger();
        ledger.add(new Trade("ACME", 100, "BUY", Optional.of("opening")));
        ledger.add(new Trade("BLUE", 50, "SELL", Optional.empty()));

        TradeIteratorService service = new TradeIteratorService(ledger);
        service.summarize().forEach(System.out::println);
    }
}
