package com.example.memento.api;

import java.util.List;

public record PortfolioSnapshot(String name, double cashBalance, List<String> holdings) {
}
