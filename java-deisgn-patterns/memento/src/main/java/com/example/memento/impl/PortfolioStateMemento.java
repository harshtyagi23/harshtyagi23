package com.example.memento.impl;

import com.example.memento.api.PortfolioMemento;
import com.example.memento.api.PortfolioSnapshot;

public record PortfolioStateMemento(PortfolioSnapshot snapshot) implements PortfolioMemento {
}
