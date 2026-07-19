package com.example.memento.impl;

import com.example.memento.api.PortfolioMemento;

import java.util.ArrayDeque;
import java.util.Deque;

public final class PortfolioCaretaker {
    private final Deque<PortfolioMemento> history = new ArrayDeque<>();

    public void push(PortfolioMemento memento) {
        history.push(memento);
    }

    public PortfolioMemento pop() {
        return history.pop();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }
}
