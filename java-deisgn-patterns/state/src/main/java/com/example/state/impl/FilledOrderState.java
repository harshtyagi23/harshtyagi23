package com.example.state.impl;

import com.example.state.api.TradeState;
import com.example.state.domain.OrderContext;

public class FilledOrderState implements TradeState {
    @Override
    public String handle(OrderContext context) {
        return switch (context.normalizedStatus()) {
            case "filled" -> "Order is filled and settled";
            case "cancelled" -> "Order was cancelled before execution";
            default -> "Order cannot be marked as filled in current state";
        };
    }
}
