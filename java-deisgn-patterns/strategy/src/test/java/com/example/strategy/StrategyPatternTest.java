package com.example.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.strategy.application.TradingSystem;
import com.example.strategy.domain.OrderType;
import com.example.strategy.impl.LimitOrderStrategy;
import com.example.strategy.impl.MarketOrderStrategy;
import org.junit.jupiter.api.Test;

class StrategyPatternTest {

    @Test
    void executesMarketOrderWithDefaultStrategy() {
        TradingSystem tradingSystem = new TradingSystem(new MarketOrderStrategy());

        String result = tradingSystem.executeOrder(1000.0, 10);

        assertEquals("Executed market order for 1000.0 shares 10", result);
    }

    @Test
    void executesLimitOrderWithCustomStrategy() {
        TradingSystem tradingSystem = new TradingSystem(new LimitOrderStrategy());

        String result = tradingSystem.executeOrder(500.0, 5);

        assertEquals("Executed limit order for 500.0 shares 5", result);
    }

    @Test
    void resolvesStrategyFromEnumAndOptional() {
        String result = new TradingSystem(TradingSystem.createStrategy(OrderType.MARKET))
                .executeOrder(OrderType.MARKET, 750.0, 8);

        assertEquals("Executed market order for 750.0 shares 8", result);
    }

    @Test
    void createsLambdaBasedStrategy() {
        String result = new TradingSystem(TradingSystem.createStrategyUsingLambda(OrderType.LIMIT))
                .executeOrder(400.0, 3);

        assertEquals("Executed limit order for 400.0 shares 3", result);
    }
}
