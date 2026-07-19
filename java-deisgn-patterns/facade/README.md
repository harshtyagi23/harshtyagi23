# Facade Pattern

This example models a trade execution facade that hides market data, risk, and execution subsystems behind one simple API.

## Why it fits

A trading client wants one entry point to execute an order, not a chain of direct calls into every subsystem. Facade reduces coupling and keeps the orchestration logic in one place.

## Structure

```mermaid
classDiagram
    class TradeFacade {
        +executeTrade(String, double, Optional~Double~)
    }

    class DefaultTradeFacade
    class MarketDataService {
        +lookupPrice(symbol)
    }

    class RiskService {
        +approve(symbol, quantity)
    }

    class ExecutionService {
        +execute(symbol, quantity, price)
    }

    class TradeConfirmation {
        +render()
    }

    TradeFacade <|.. DefaultTradeFacade
    DefaultTradeFacade --> MarketDataService
    DefaultTradeFacade --> RiskService
    DefaultTradeFacade --> ExecutionService
    DefaultTradeFacade --> TradeConfirmation
```

## Java features used

- `Optional` for missing price flow
- Records for immutable trade confirmations
- `switch` expressions in market data lookup
- Clear service boundaries to keep orchestration readable
