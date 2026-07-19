package com.example.templatemethod;

import com.example.templatemethod.domain.PortfolioReviewRequest;
import com.example.templatemethod.impl.AggressiveReview;
import com.example.templatemethod.impl.ConservativeReview;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateMethodTest {

    @Test
    void shouldUseConservativeReviewTemplate() {
        ConservativeReview review = new ConservativeReview();
        String result = review.review(new PortfolioReviewRequest("acct-001", 120000));

        assertTrue(result.contains("Conservative review started"));
        assertTrue(result.contains("Low risk"));
    }

    @Test
    void shouldUseAggressiveReviewTemplate() {
        AggressiveReview review = new AggressiveReview();
        String result = review.review(new PortfolioReviewRequest("acct-002", 250000));

        assertTrue(result.contains("Aggressive review started"));
        assertTrue(result.contains("High opportunity risk"));
    }
}
