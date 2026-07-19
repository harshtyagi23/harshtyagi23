# Composite Pattern

This example models a portfolio tree where both individual holdings and nested portfolio groups are treated as portfolio components. The same operations work on both leaves and composites.

## Why it fits

Financial portfolios naturally form trees: a portfolio can contain positions, cash, and other sub-portfolios. Composite lets the client treat the whole structure uniformly while the group object handles aggregation.

## Structure

```mermaid
classDiagram
    class PortfolioComponent {
        +name()
        +marketValue()
        +describe()
    }

    class PortfolioNode {
        +children()
        +isLeaf()
    }

    class Holding
    class CashPosition
    class PortfolioGroup {
        +add(component)
    }

    class PortfolioSummary {
        +totalValue()
        +render()
    }

    PortfolioComponent <|.. Holding
    PortfolioComponent <|.. CashPosition
    PortfolioComponent <|.. PortfolioNode
    PortfolioNode <|.. PortfolioGroup
    PortfolioSummary o-- PortfolioComponent
```

## Java features used

- Records for immutable leaf components and summary data
- `Optional` for missing sector metadata and strategy notes
- Stream aggregation for tree value calculation
- A small immutable API surface to keep the tree easy to reason about
