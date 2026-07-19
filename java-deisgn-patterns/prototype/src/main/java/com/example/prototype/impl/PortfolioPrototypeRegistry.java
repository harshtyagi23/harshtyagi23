package com.example.prototype.impl;

import com.example.prototype.api.PortfolioTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class PortfolioPrototypeRegistry {
    private final Map<String, PortfolioTemplate> prototypes = new ConcurrentHashMap<>();

    public void register(String key, PortfolioTemplate template) {
        prototypes.put(key, template);
    }

    public PortfolioTemplate clone(String key) {
        return Optional.ofNullable(prototypes.get(key))
                .map(PortfolioTemplate::copy)
                .orElseThrow(() -> new IllegalArgumentException("Unknown prototype: " + key));
    }
}
