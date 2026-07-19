package com.example.strategy.api;

@FunctionalInterface
public interface Observer<T> {
    void update(T event);
}
