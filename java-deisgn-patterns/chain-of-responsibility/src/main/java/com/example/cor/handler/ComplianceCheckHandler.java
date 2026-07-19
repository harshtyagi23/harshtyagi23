package com.example.cor.handler;

import com.example.cor.api.TradeDecision;
import com.example.cor.api.TradeRequest;

import java.util.List;
import java.util.Optional;

public final class ComplianceCheckHandler extends TradeApprovalHandler {
    private static final List<String> RESTRICTED_INSTRUMENTS = List.of("SANCTIONED-BOND", "BLACKLISTED-SWAP");

    @Override
    protected Optional<TradeDecision> evaluate(TradeRequest request) {
        return RESTRICTED_INSTRUMENTS.stream()
                .filter(request.instrument()::equalsIgnoreCase)
                .findFirst()
                .map(instrument -> TradeDecision.rejected(
                        "ComplianceCheck",
                        "Instrument %s is restricted for this desk".formatted(instrument)));
    }
}
