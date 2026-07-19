package com.example.iterator.impl;

import com.example.iterator.api.Trade;
import com.example.iterator.api.TradeLedger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ArrayTradeLedger implements TradeLedger {
    private final List<Trade> trades = new ArrayList<>();

    @Override
    public void add(Trade trade) {
        trades.add(trade);
    }

    @Override
    public List<Trade> snapshot() {
        return List.copyOf(trades);
    }

    @Override
    public Iterator<Trade> iterator() {
        return snapshot().iterator();
    }
}
