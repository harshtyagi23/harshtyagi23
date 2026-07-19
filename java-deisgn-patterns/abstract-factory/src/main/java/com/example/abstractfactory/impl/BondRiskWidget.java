package com.example.abstractfactory.impl;

import com.example.abstractfactory.api.RiskWidget;

import java.util.Optional;

public record BondRiskWidget(Optional<Integer> riskScore) implements RiskWidget {
    @Override
    public String marketFamily() {
        return "bond";
    }

    @Override
    public String render() {
        return "bond risk " + riskScore.map(score -> "score " + score).orElse("not rated");
    }
}
