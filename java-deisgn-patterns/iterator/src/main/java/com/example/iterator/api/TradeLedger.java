package com.example.iterator.api;

import java.util.Iterator;
import java.util.List;

public interface TradeLedger extends Iterable<Trade> {
    void add(Trade trade);

    List<Trade> snapshot();

    Iterator<Trade> iterator();
}
