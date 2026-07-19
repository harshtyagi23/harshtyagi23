# Java Design Patterns Plan

## 1. Pattern inventory

### Behavioral patterns
- Strategy
- Observer
- Command
- State
- Chain of Responsibility
- Interpreter
- Iterator
- Mediator
- Memento
- Visitor
- Template Method

### Structural patterns
- Adapter
- Bridge
- Composite
- Decorator
- Facade
- Flyweight
- Proxy

### Creational patterns
- Singleton
- Factory Method
- Abstract Factory
- Builder
- Prototype

## 2. Overall implementation plan for review

### Goal
Build a Gradle-based Java 25 learning project that demonstrates each design pattern using finance-domain examples such as Fund, ShareClass, FinancialProduct, Trade, Account, and Balance.

### Working approach
For each pattern, I will follow this sequence:
1. Create a reviewable plan for the specific pattern.
2. Show the plan to you for feedback.
3. Create a dedicated branch named `feature/<pattern-name>`.
4. Implement a working Java example with Gradle.
5. Add unit tests with JUnit 5.
6. Add a markdown explanation file with:
   - a short summary of the pattern
   - a Mermaid class diagram
   - bullet points explaining why the pattern fits
   - notes on how SOLID principles are applied
7. Commit and push the branch.
8. Move to the next pattern.

### Repository structure proposal
- Keep one Gradle project for the learning repository.
- Each pattern example will be implemented in its own branch and folder structure.
- Each example should stay self-contained and easy to review.

### Standards to follow
- Java 25
- Gradle
- JUnit 5
- Mockito only when needed
- Google Java Style Guide
- Domain-driven examples using finance objects

### Suggested implementation order
1. Strategy
2. Observer
3. Decorator
4. Factory Method
5. Builder
6. State
7. Adapter
8. Proxy
9. Command
10. Template Method
11. Singleton
12. Abstract Factory
13. Composite
14. Facade
15. Bridge
16. Flyweight
17. Interpreter
18. Iterator
19. Mediator
20. Memento
21. Visitor
22. Prototype
23. Chain of Responsibility

### First step proposal
Start with Strategy as the first example because it is simple, practical, and easy to explain with a financial trading scenario.

## 3. Review notes
Please review this inventory and sequence. If you want, I can next:
- refine the order,
- choose a different first pattern,
- or start implementing the first example immediately.