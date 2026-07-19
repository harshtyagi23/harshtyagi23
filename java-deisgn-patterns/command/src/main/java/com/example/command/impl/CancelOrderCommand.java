package com.example.command.impl;

import com.example.command.api.OrderCommand;
import com.example.command.domain.OrderRequest;

public class CancelOrderCommand implements OrderCommand {
    private final OrderRequest request;

    public CancelOrderCommand(OrderRequest request) {
        this.request = request;
    }

    @Override
    public String execute() {
        return "Cancelled %s for %s".formatted(request.action(), request.symbol());
    }
}
