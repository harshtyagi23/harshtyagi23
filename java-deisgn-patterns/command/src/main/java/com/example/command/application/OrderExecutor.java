package com.example.command.application;

import com.example.command.api.OrderCommand;
import com.example.command.domain.OrderRequest;
import com.example.command.impl.CancelOrderCommand;
import com.example.command.impl.PlaceOrderCommand;

import java.util.Optional;

public class OrderExecutor {
    public String execute(OrderCommand command) {
        return Optional.ofNullable(command)
                .map(OrderCommand::execute)
                .orElse("No command available");
    }

    public String execute(String action, String symbol) {
        OrderRequest request = new OrderRequest(symbol, action);
        return switch (action.toLowerCase()) {
            case "buy" -> execute(new PlaceOrderCommand(request));
            case "sell" -> execute(new PlaceOrderCommand(request));
            case "cancel" -> execute(new CancelOrderCommand(request));
            default -> "Unsupported action";
        };
    }
}
