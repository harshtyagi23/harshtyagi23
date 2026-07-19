package com.example.strategy.api;

@FunctionalInterface
public interface OrderStrategy {
    String execute(double price, int shares);
}
