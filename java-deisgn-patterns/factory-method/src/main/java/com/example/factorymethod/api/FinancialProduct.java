package com.example.factorymethod.api;

public sealed interface FinancialProduct permits EquityProduct, BondProduct {
    String describe();
}

record EquityProduct(String symbol) implements FinancialProduct {
    @Override
    public String describe() {
        return "Equity product: " + symbol;
    }
}

record BondProduct(String issuer, String maturity) implements FinancialProduct {
    @Override
    public String describe() {
        return "Bond product: " + issuer + " maturing in " + maturity;
    }
}
