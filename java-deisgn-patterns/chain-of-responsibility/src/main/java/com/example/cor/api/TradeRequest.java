package com.example.cor.api;

import java.math.BigDecimal;
import java.util.Optional;

public record TradeRequest(
        String desk,
        String clientTier,
        String instrument,
        BigDecimal quantity,
        BigDecimal notional,
        Optional<String> traderNote) {

    public TradeRequest {
        traderNote = traderNote == null ? Optional.empty() : traderNote;
    }
}
