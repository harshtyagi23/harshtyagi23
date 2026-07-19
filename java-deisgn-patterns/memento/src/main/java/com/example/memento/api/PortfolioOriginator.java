package com.example.memento.api;

public interface PortfolioOriginator {
    PortfolioMemento save();

    void restore(PortfolioMemento memento);
}
