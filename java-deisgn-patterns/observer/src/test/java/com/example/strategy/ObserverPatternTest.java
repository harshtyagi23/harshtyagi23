package com.example.strategy;

import com.example.strategy.application.MarketFeed;
import com.example.strategy.domain.MarketEvent;
import com.example.strategy.impl.TradingDesk;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverPatternTest {

    @Test
    void shouldNotifyRegisteredObservers() {
        MarketFeed feed = new MarketFeed();
        List<String> notifications = new ArrayList<>();

        TradingDesk deskOne = new TradingDesk("Desk 1", notifications::add);
        TradingDesk deskTwo = new TradingDesk("Desk 2", notifications::add);

        feed.registerObserver(event -> notifications.add("Desk A:" + event.symbol() + ":" + event.price()));
        feed.registerObserver(event -> notifications.add("Desk B:" + event.symbol() + ":" + event.price()));
        feed.registerObserver(deskOne::update);
        feed.registerObserver(deskTwo::update);

        feed.publishEvent(new MarketEvent("AAPL", 198.75));

        assertEquals(4, notifications.size());
    }
}
