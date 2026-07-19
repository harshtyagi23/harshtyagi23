package com.example.cor.handler;

import com.example.cor.api.TradeDecision;
import com.example.cor.api.TradeRequest;

import java.math.BigDecimal;
import java.util.Optional;

public final class RiskLimitHandler extends TradeApprovalHandler {
    @Override
    protected Optional<TradeDecision> evaluate(TradeRequest request) {
        BigDecimal limit = switch (request.clientTier().toUpperCase()) {
            case "INSTITUTIONAL" -> BigDecimal.valueOf(25_000_000L);
            case "PRIVATE" -> BigDecimal.valueOf(5_000_000L);
            default -> BigDecimal.valueOf(1_000_000L);
        };

        if (request.notional().compareTo(limit) > 0) {
            return Optional.of(TradeDecision.rejected(
                    "RiskLimit",
                    "Notional %s exceeds %s limit for %s".formatted(request.notional(), limit, request.clientTier())));
        }

        return Optional.empty();
    }
}
