package com.example.abstractfactory.impl;

import com.example.abstractfactory.api.RiskWidget;

import java.util.Optional;

public record EquityRiskWidget(Optional<Integer> riskScore) implements RiskWidget {
    @Override
    public String marketFamily() {
        return "equity";
    }

    @Override
    public String render() {
        return "equity risk " + riskScore.map(score -> "score " + score).orElse("not rated");
    }
}
