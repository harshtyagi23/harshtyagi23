# Abstract Factory Pattern

This example models market dashboard creation for different asset classes. A single factory family creates a matching quote widget and risk widget so each dashboard stays internally consistent.

## Why this fits

Equities and bonds need related presentation components, but the concrete classes differ. Abstract factory keeps those families aligned without scattering `if` and `switch` logic throughout the codebase.

## Structure

```mermaid
classDiagram
    class MarketDashboardFactory {
        +createQuoteWidget(String, Optional~Double~)
        +createRiskWidget(Optional~Integer~)
        +marketFamily()
    }

    class EquityMarketDashboardFactory
    class BondMarketDashboardFactory
    class QuoteWidget
    class RiskWidget
    class MarketDashboard

    MarketDashboardFactory <|.. EquityMarketDashboardFactory
    MarketDashboardFactory <|.. BondMarketDashboardFactory
    MarketDashboard o-- QuoteWidget
    MarketDashboard o-- RiskWidget
```

## Java features used

- `switch` expressions to choose a factory family
- `record` types for immutable widgets and dashboard composition
- `sealed` interfaces to constrain the allowed product families
- `Optional` to model missing market data without `null`

## Problem it solves

This pattern addresses a recurring design issue by separating responsibilities and reducing tight coupling in Abstract Factory Pattern scenarios.

## When to use

- Use this pattern when you need cleaner separation of concerns in Abstract Factory Pattern code.
- Use it when you expect variation in behavior and want to avoid complex conditionals.
- Use it when maintainability and extension are more important than one-off shortcuts.
