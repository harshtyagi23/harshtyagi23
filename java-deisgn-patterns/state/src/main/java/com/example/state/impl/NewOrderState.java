package com.example.state.impl;

import com.example.state.api.TradeState;
import com.example.state.domain.OrderContext;

public class NewOrderState implements TradeState {
    @Override
    public String handle(OrderContext context) {
        return switch (context.normalizedStatus()) {
            case "new" -> "Order is accepted for processing";
            case "pending" -> "Order waits for approval";
            default -> "Order status is not recognized";
        };
    }
}
