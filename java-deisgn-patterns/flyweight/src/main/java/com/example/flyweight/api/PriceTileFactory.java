package com.example.flyweight.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PriceTileFactory {
    private final Map<String, PriceTile> cache = new ConcurrentHashMap<>();

    public PriceTile getTile(String symbol) {
        return cache.computeIfAbsent(symbol, key -> new com.example.flyweight.impl.DefaultPriceTile(key));
    }

    public int cacheSize() {
        return cache.size();
    }
}
