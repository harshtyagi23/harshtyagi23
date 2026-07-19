package com.example.state;

import com.example.state.application.OrderProcessor;
import com.example.state.domain.OrderContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatePatternTest {

    @Test
    void shouldProcessNewOrdersUsingStateTransitions() {
        OrderProcessor processor = OrderProcessor.createDefault();
        String result = processor.process(new OrderContext("NEW", 1000));

        assertEquals("Order is accepted for processing", result);
    }

    @Test
    void shouldHandleApprovedOrdersWithDedicatedState() {
        OrderProcessor processor = OrderProcessor.createApproved();
        String result = processor.process(new OrderContext("APPROVED", 5000));

        assertEquals("Order is approved and ready for execution", result);
    }

    @Test
    void shouldHandleFilledOrdersWithSettlementState() {
        OrderProcessor processor = OrderProcessor.createFilled();
        String result = processor.process(new OrderContext("FILLED", 2500));

        assertEquals("Order is filled and settled", result);
    }
}
