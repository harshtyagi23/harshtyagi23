package com.example.command.impl;

import com.example.command.api.OrderCommand;
import com.example.command.domain.OrderRequest;

public class PlaceOrderCommand implements OrderCommand {
    private final OrderRequest request;

    public PlaceOrderCommand(OrderRequest request) {
        this.request = request;
    }

    @Override
    public String execute() {
        return "Placed %s for %s".formatted(request.action(), request.symbol());
    }
}
