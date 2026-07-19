package com.example.state.impl;

import com.example.state.api.TradeState;
import com.example.state.domain.OrderContext;

public class ApprovedOrderState implements TradeState {
    @Override
    public String handle(OrderContext context) {
        return switch (context.normalizedStatus()) {
            case "approved" -> "Order is approved and ready for execution";
            case "filled" -> "Order has already been filled";
            default -> "Order cannot be approved in current state";
        };
    }
}
