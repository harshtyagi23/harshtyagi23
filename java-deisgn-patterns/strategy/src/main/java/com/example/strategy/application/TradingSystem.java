package com.example.strategy.application;

import com.example.strategy.api.OrderStrategy;
import com.example.strategy.domain.OrderType;
import com.example.strategy.domain.TradeOrder;
import com.example.strategy.impl.LimitOrderStrategy;
import com.example.strategy.impl.MarketOrderStrategy;
import java.util.Optional;
import java.util.function.Function;

public class TradingSystem {
    private final OrderStrategy orderStrategy;

    public TradingSystem(OrderStrategy orderStrategy) {
        this.orderStrategy = orderStrategy;
    }

    public String executeOrder(double price, int shares) {
        TradeOrder order = new TradeOrder(price, shares);
        return orderStrategy.execute(order.price(), order.shares());
    }

    public String executeOrder(OrderType orderType, double price, int shares) {
        TradeOrder order = new TradeOrder(price, shares);
        return resolveStrategy(orderType)
                .map(strategy -> strategy.execute(order.price(), order.shares()))
                .orElseGet(() -> "No strategy found for order type: " + orderType);
    }

    public static OrderStrategy createStrategy(OrderType orderType) {
        return switch (orderType) {
            case MARKET -> new MarketOrderStrategy();
            case LIMIT -> new LimitOrderStrategy();
        };
    }

    public static OrderStrategy createStrategyUsingLambda(OrderType orderType) {
        Function<OrderType, OrderStrategy> strategyFactory = type -> switch (type) {
            case MARKET -> (price, shares) -> "Executed market order for " + price + " shares " + shares;
            case LIMIT -> (price, shares) -> "Executed limit order for " + price + " shares " + shares;
        };

        return strategyFactory.apply(orderType);
    }

    private Optional<OrderStrategy> resolveStrategy(OrderType orderType) {
        return Optional.ofNullable(orderType)
                .map(type -> switch (type) {
                    case MARKET -> new MarketOrderStrategy();
                    case LIMIT -> new LimitOrderStrategy();
                });
    }
}
