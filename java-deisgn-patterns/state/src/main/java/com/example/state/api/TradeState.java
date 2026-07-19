package com.example.state.api;

import com.example.state.domain.OrderContext;

public interface TradeState {
    String handle(OrderContext context);
}
