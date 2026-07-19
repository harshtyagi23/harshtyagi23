package com.example.command;

import com.example.command.application.OrderExecutor;
import com.example.command.domain.OrderRequest;
import com.example.command.impl.CancelOrderCommand;
import com.example.command.impl.PlaceOrderCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandPatternTest {

    @Test
    void shouldExecutePlaceOrderCommand() {
        OrderExecutor executor = new OrderExecutor();
        String result = executor.execute(new PlaceOrderCommand(new OrderRequest("MSFT", "buy")));

        assertEquals("Placed buy for MSFT", result);
    }

    @Test
    void shouldExecuteCancelCommandFromStringInput() {
        OrderExecutor executor = new OrderExecutor();
        String result = executor.execute("cancel", "AAPL");

        assertEquals("Cancelled cancel for AAPL", result);
    }

    @Test
    void shouldExecuteCancelCommandObject() {
        OrderExecutor executor = new OrderExecutor();
        String result = executor.execute(new CancelOrderCommand(new OrderRequest("AAPL", "cancel")));

        assertEquals("Cancelled cancel for AAPL", result);
    }
}
