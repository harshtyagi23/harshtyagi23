package com.example.cor.handler;

import com.example.cor.api.TradeDecision;
import com.example.cor.api.TradeRequest;

import java.util.Optional;

public final class DeskApprovalHandler extends TradeApprovalHandler {
    @Override
    protected Optional<TradeDecision> evaluate(TradeRequest request) {
        String approvalMessage = request.traderNote()
                .map(note -> "Desk approved after review: " + note)
                .orElse("Desk approved for straight-through processing");
        return Optional.of(TradeDecision.approved("DeskApproval", approvalMessage));
    }
}
