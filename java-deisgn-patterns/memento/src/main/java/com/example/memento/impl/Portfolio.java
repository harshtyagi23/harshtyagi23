package com.example.memento.impl;

import com.example.memento.api.PortfolioMemento;
import com.example.memento.api.PortfolioOriginator;
import com.example.memento.api.PortfolioSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Portfolio implements PortfolioOriginator {
    private String name;
    private double cashBalance;
    private final List<String> holdings = new ArrayList<>();

    public Portfolio(String name, double cashBalance) {
        this.name = name;
        this.cashBalance = cashBalance;
    }

    public void addHolding(String holding) {
        holdings.add(holding);
    }

    public void adjustCash(double delta) {
        cashBalance += delta;
    }

    public String name() {
        return name;
    }

    public double cashBalance() {
        return cashBalance;
    }

    public List<String> holdings() {
        return List.copyOf(holdings);
    }

    public Optional<String> primaryHolding() {
        return holdings.stream().findFirst();
    }

    @Override
    public PortfolioMemento save() {
        return new PortfolioStateMemento(new PortfolioSnapshot(name, cashBalance, List.copyOf(holdings)));
    }

    @Override
    public void restore(PortfolioMemento memento) {
        PortfolioSnapshot snapshot = memento.snapshot();
        this.name = snapshot.name();
        this.cashBalance = snapshot.cashBalance();
        this.holdings.clear();
        this.holdings.addAll(snapshot.holdings());
    }

    public String render() {
        return name + " cash=" + String.format("%.2f", cashBalance) + " holdings=" + holdings;
    }
}
