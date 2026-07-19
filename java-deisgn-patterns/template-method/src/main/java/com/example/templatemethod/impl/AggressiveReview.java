package com.example.templatemethod.impl;

import com.example.templatemethod.api.PortfolioReviewTemplate;
import com.example.templatemethod.domain.PortfolioReviewRequest;

public class AggressiveReview extends PortfolioReviewTemplate {
    @Override
    protected String startReview(PortfolioReviewRequest request) {
        return "Aggressive review started for %s".formatted(request.accountId());
    }

    @Override
    protected String assessRisk(PortfolioReviewRequest request) {
        return switch ((int) Math.round(request.balance() / 100000)) {
            case 0 -> "Very low risk";
            case 1, 2 -> "Moderate risk";
            default -> "High opportunity risk";
        };
    }

    @Override
    protected String recommendAction(PortfolioReviewRequest request) {
        return request.balance() > 200000 ? "Increase equity exposure" : "Hold and monitor";
    }
}
