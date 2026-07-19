package com.example.state.application;

import com.example.state.api.TradeState;
import com.example.state.domain.OrderContext;
import com.example.state.impl.ApprovedOrderState;
import com.example.state.impl.FilledOrderState;
import com.example.state.impl.NewOrderState;

import java.util.Optional;
import java.util.function.Supplier;

public class OrderProcessor {
    private final TradeState state;

    public OrderProcessor(TradeState state) {
        this.state = state;
    }

    public String process(OrderContext context) {
        return Optional.ofNullable(state)
                .map(currentState -> currentState.handle(context))
                .orElse("No state configured");
    }

    public static OrderProcessor createDefault() {
        Supplier<TradeState> stateSupplier = NewOrderState::new;
        return new OrderProcessor(stateSupplier.get());
    }

    public static OrderProcessor createApproved() {
        Supplier<TradeState> stateSupplier = ApprovedOrderState::new;
        return new OrderProcessor(stateSupplier.get());
    }

    public static OrderProcessor createFilled() {
        Supplier<TradeState> stateSupplier = FilledOrderState::new;
        return new OrderProcessor(stateSupplier.get());
    }
}
