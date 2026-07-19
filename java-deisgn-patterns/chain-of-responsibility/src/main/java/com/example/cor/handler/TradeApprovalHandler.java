package com.example.cor.handler;

import com.example.cor.api.TradeDecision;
import com.example.cor.api.TradeRequest;

import java.util.Objects;
import java.util.Optional;

public abstract class TradeApprovalHandler {
    private TradeApprovalHandler next;

    public TradeApprovalHandler linkWith(TradeApprovalHandler next) {
        this.next = next;
        return next;
    }

    public final TradeDecision handle(TradeRequest request) {
        Objects.requireNonNull(request, "request");
        Optional<TradeDecision> decision = evaluate(request);
        if (decision.isPresent() || next == null) {
            return decision.orElseGet(() -> TradeDecision.approved(getClass().getSimpleName(), "Passed all checks"));
        }
        return next.handle(request);
    }

    protected abstract Optional<TradeDecision> evaluate(TradeRequest request);
}
