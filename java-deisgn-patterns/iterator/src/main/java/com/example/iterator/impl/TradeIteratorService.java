package com.example.iterator.impl;

import com.example.iterator.api.Trade;
import com.example.iterator.api.TradeLedger;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public final class TradeIteratorService {
    private final TradeLedger ledger;

    public TradeIteratorService(TradeLedger ledger) {
        this.ledger = ledger;
    }

    public List<String> summarize() {
        return StreamSupport.stream(ledger.spliterator(), false)
                .map(this::summarizeTrade)
                .toList();
    }

    private String summarizeTrade(Trade trade) {
        return trade.symbol() + " " + trade.side() + " " + trade.quantity()
                + trade.note().map(note -> " (" + note + ")").orElse("");
    }

    public Optional<Trade> firstTrade() {
        return ledger.iterator().hasNext() ? Optional.of(ledger.iterator().next()) : Optional.empty();
    }
}
