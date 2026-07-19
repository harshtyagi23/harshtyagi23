package com.example.cor;

import com.example.cor.api.TradeDecision;
import com.example.cor.api.TradeRequest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChainOfResponsibilityTest {
    private final TradeApprovalService service = new TradeApprovalService();

    @Test
    void approvesTradesThatPassAllHandlers() {
        TradeDecision decision = service.review(new TradeRequest(
                "Equity Desk",
                "private",
                "ACME",
                BigDecimal.valueOf(50_000),
                BigDecimal.valueOf(2_500_000),
                Optional.of("Quarter-end rebalance")));

        assertTrue(decision.approved());
        assertEquals("DeskApproval", decision.stage());
        assertEquals("Desk approved after review: Quarter-end rebalance", decision.message());
    }

    @Test
    void rejectsRestrictedInstrumentsBeforeRiskReview() {
        TradeDecision decision = service.review(new TradeRequest(
                "Credit Desk",
                "institutional",
                "SANCTIONED-BOND",
                BigDecimal.valueOf(10_000),
                BigDecimal.valueOf(1_500_000),
                Optional.empty()));

        assertFalse(decision.approved());
        assertEquals("ComplianceCheck", decision.stage());
        assertEquals("Instrument SANCTIONED-BOND is restricted for this desk", decision.message());
    }

    @Test
    void rejectsTradesThatBreakRiskLimits() {
        TradeDecision decision = service.review(new TradeRequest(
                "Equity Desk",
                "retail",
                "ACME",
                BigDecimal.valueOf(2_500),
                BigDecimal.valueOf(2_500_000),
                Optional.empty()));

        assertFalse(decision.approved());
        assertEquals("RiskLimit", decision.stage());
        assertEquals("Notional 2500000 exceeds 1000000 limit for retail", decision.message());
    }
}
