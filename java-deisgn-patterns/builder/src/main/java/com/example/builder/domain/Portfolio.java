package com.example.builder.domain;

public record Portfolio(String name, String riskLevel, double allocation, String benchmark) {
    public String summary() {
        return switch (riskLevel.toLowerCase()) {
            case "aggressive" -> "High-growth portfolio";
            case "moderate" -> "Balanced portfolio";
            case "conservative" -> "Defensive portfolio";
            default -> "Managed portfolio";
        };
    }
}
