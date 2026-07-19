package com.example.templatemethod.api;

import com.example.templatemethod.domain.PortfolioReviewRequest;

import java.util.Optional;

public abstract class PortfolioReviewTemplate {
    public final String review(PortfolioReviewRequest request) {
        return Optional.ofNullable(request)
                .map(this::prepareReview)
                .orElse("No review request available");
    }

    protected String prepareReview(PortfolioReviewRequest request) {
        String summary = startReview(request);
        String riskAssessment = assessRisk(request);
        String recommendation = recommendAction(request);
        return "%s | %s | %s".formatted(summary, riskAssessment, recommendation);
    }

    protected abstract String startReview(PortfolioReviewRequest request);

    protected abstract String assessRisk(PortfolioReviewRequest request);

    protected abstract String recommendAction(PortfolioReviewRequest request);
}
