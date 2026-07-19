package com.example.strategy.application;

import com.example.strategy.api.Observer;
import com.example.strategy.domain.MarketEvent;

import java.util.ArrayList;
import java.util.List;

public class MarketFeed {
    private final List<Observer<MarketEvent>> observers = new ArrayList<>();

    public void registerObserver(Observer<MarketEvent> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<MarketEvent> observer) {
        observers.remove(observer);
    }

    public void publishEvent(MarketEvent event) {
        observers.forEach(observer -> observer.update(event));
    }
}
