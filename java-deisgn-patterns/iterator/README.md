# Iterator Pattern

This example models a trade ledger that can be traversed without exposing its internal storage. The ledger provides a stable iteration surface while the implementation stays hidden.

## Why it fits

A blotter or trade ledger should be easy to walk in order, but callers should not care whether the data is stored in a list, tree, or some other structure. Iterator separates traversal from storage.

## Structure

```mermaid
classDiagram
    class TradeLedger {
        +add(Trade)
        +snapshot()
        +iterator()
    }

    class ArrayTradeLedger
    class TradeIteratorService {
        +summarize()
        +firstTrade()
    }

    class Trade

    TradeLedger <|.. ArrayTradeLedger
    TradeLedger --> Trade
    TradeIteratorService --> TradeLedger
```

## Java features used

- Records for compact trade data
- `Optional` for trade notes
- `Iterable` and `Iterator` for explicit traversal
- Stream support for turning iteration results into summaries

## Problem it solves

This pattern addresses a recurring design issue by separating responsibilities and reducing tight coupling in Iterator Pattern scenarios.

## When to use

- Use this pattern when you need cleaner separation of concerns in Iterator Pattern code.
- Use it when you expect variation in behavior and want to avoid complex conditionals.
- Use it when maintainability and extension are more important than one-off shortcuts.

## Example from Java/JDK

Iterator is a core traversal abstraction across collections.

- JDK classes: java.util.Iterator, java.util.Spliterator
- Reference: https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Iterator.html
