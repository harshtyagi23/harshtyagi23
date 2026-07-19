package com.example.cor;

import com.example.cor.api.TradeDecision;
import com.example.cor.api.TradeRequest;
import com.example.cor.handler.ComplianceCheckHandler;
import com.example.cor.handler.DeskApprovalHandler;
import com.example.cor.handler.RiskLimitHandler;

public final class TradeApprovalService {
    private final ComplianceCheckHandler complianceCheckHandler = new ComplianceCheckHandler();
    private final RiskLimitHandler riskLimitHandler = new RiskLimitHandler();
    private final DeskApprovalHandler deskApprovalHandler = new DeskApprovalHandler();

    public TradeApprovalService() {
        complianceCheckHandler.linkWith(riskLimitHandler).linkWith(deskApprovalHandler);
    }

    public TradeDecision review(TradeRequest request) {
        return complianceCheckHandler.handle(request);
    }
}
