# Prototype Pattern

This example models portfolio templates that can be cloned and then customized. The registry stores a prototype, and callers receive a deep copy so they can modify it independently.

## Why it fits

A common portfolio shape is often reused as a starting point for new funds or model portfolios. Prototype avoids rebuilding the same structure from scratch and keeps the original template intact.

## Structure

```mermaid
classDiagram
    class Prototype~T~ {
        +copy()
    }

    class PortfolioTemplate {
        +name()
        +riskLevel()
        +holdings()
        +benchmark()
        +addHolding(String)
        +setBenchmark(String)
    }

    class DefaultPortfolioTemplate
    class PortfolioPrototypeRegistry

    Prototype <|.. PortfolioTemplate
    PortfolioTemplate <|.. DefaultPortfolioTemplate
    PortfolioPrototypeRegistry --> PortfolioTemplate
```

## Java features used

- Generics for a reusable prototype contract
- `Optional` for benchmark metadata
- Copy constructors for deep-copy behavior
- Concurrent registry for safe template lookup

## Problem it solves

This pattern addresses a recurring design issue by separating responsibilities and reducing tight coupling in Prototype Pattern scenarios.

## When to use

- Use this pattern when you need cleaner separation of concerns in Prototype Pattern code.
- Use it when you expect variation in behavior and want to avoid complex conditionals.
- Use it when maintainability and extension are more important than one-off shortcuts.

## Example from Java/JDK

Prototype appears where cloning creates new objects from existing instances.

- JDK classes: java.lang.Cloneable, java.util.Date#clone
- Reference: https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/Cloneable.html
