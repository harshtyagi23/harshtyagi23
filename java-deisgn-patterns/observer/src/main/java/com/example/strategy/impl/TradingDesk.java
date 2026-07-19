package com.example.strategy.impl;

import com.example.strategy.api.Observer;
import com.example.strategy.domain.MarketEvent;

import java.util.function.Consumer;

public class TradingDesk implements Observer<MarketEvent> {
    private final String name;
    private final Consumer<String> notifier;

    public TradingDesk(String name) {
        this(name, System.out::println);
    }

    public TradingDesk(String name, Consumer<String> notifier) {
        this.name = name;
        this.notifier = notifier;
    }

    @Override
    public void update(MarketEvent event) {
        String message = name + " received " + event.symbol() + " at " + event.price();
        notifier.accept(message);
    }
}
