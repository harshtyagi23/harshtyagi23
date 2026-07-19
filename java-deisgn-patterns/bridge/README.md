# Bridge Pattern

This example models a portfolio report abstraction that can be rendered through different output channels. The report structure stays separate from the rendering implementation, so both can evolve independently.

## Why it fits

A report has a stable concept, but the output format changes often. Bridge lets the portfolio report focus on business content while renderer implementations handle presentation details.

## Structure

```mermaid
classDiagram
    class Report {
        -ReportRenderer renderer
        +publish()
    }

    class ReportRenderer {
        +renderHeader(String)
        +renderBody(String)
        +renderFooter(String)
    }

    class PortfolioReport
    class PlainTextRenderer
    class MarkdownRenderer

    Report <|-- PortfolioReport
    ReportRenderer <|.. PlainTextRenderer
    ReportRenderer <|.. MarkdownRenderer
    Report --> ReportRenderer
```

## Java features used

- Abstract base class for the report abstraction
- Interface-based renderer implementations
- `System.lineSeparator()` for platform-aware formatting
- A small immutable object graph that keeps presentation and behavior separate
