package com.example.factorymethod.api;

import java.util.Optional;
import java.util.function.Function;

public interface ProductFactory {
    FinancialProduct create(String type);

    static ProductFactory defaultFactory() {
        Function<String, Optional<FinancialProduct>> factory = type -> switch (type.toLowerCase()) {
            case "equity" -> Optional.of(new EquityProduct("AAPL"));
            case "bond" -> Optional.of(new BondProduct("Contoso Bank", "2035"));
            default -> Optional.empty();
        };

        return input -> factory.apply(input).orElse(null);
    }
}
