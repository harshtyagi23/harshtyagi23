package com.example.templatemethod.impl;

import com.example.templatemethod.api.PortfolioReviewTemplate;
import com.example.templatemethod.domain.PortfolioReviewRequest;

public class ConservativeReview extends PortfolioReviewTemplate {
    @Override
    protected String startReview(PortfolioReviewRequest request) {
        return "Conservative review started for %s".formatted(request.accountId());
    }

    @Override
    protected String assessRisk(PortfolioReviewRequest request) {
        return switch ((int) Math.round(request.balance() / 100000)) {
            case 0, 1 -> "Low risk";
            case 2 -> "Medium risk";
            default -> "High risk";
        };
    }

    @Override
    protected String recommendAction(PortfolioReviewRequest request) {
        return request.balance() > 150000 ? "Allocate to cash reserves" : "Maintain current holdings";
    }
}
