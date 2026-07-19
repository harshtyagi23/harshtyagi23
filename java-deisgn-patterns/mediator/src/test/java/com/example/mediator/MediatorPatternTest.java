package com.example.mediator;

import com.example.mediator.impl.EquityTrader;
import com.example.mediator.impl.FixedIncomeTrader;
import com.example.mediator.impl.TradeDesk;
import com.example.mediator.impl.TradeDeskMediator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MediatorPatternTest {
    @Test
    void routesTradesThroughTheMediator() {
        TradeDesk desk = new TradeDesk(new TradeDeskMediator(), new EquityTrader(), new FixedIncomeTrader());

        var equityDecision = desk.equityTrader().requestTrade("ACME", 100);
        var bondDecision = desk.fixedIncomeTrader().requestTrade("UST10Y", 250);

        assertTrue(equityDecision.approved());
        assertEquals("APPROVED equity-routing - BUY ACME x 100.0", equityDecision.render());
        assertTrue(bondDecision.approved());
        assertEquals("APPROVED fixed-income-routing - SELL UST10Y x 250.0", bondDecision.render());
    }

    @Test
    void rejectsUnsupportedRequests() {
        TradeDeskMediator mediator = new TradeDeskMediator();

        var decision = mediator.submitTrade("derivatives", "", -1, "BUY");

        assertFalse(decision.approved());
        assertEquals("REJECTED derivatives - quantity must be positive", decision.render());
    }
}
