package com.example.flyweight.impl;

import com.example.flyweight.api.PriceTile;
import com.example.flyweight.api.PriceTileFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class QuoteBoard {
    private final PriceTileFactory factory;

    public QuoteBoard(PriceTileFactory factory) {
        this.factory = factory;
    }

    public List<String> renderQuotes(List<QuoteRequest> requests) {
        return requests.stream()
                .map(request -> factory.getTile(request.symbol())
                        .render(request.marketPrice(), request.venue()))
                .toList();
    }

    public int cachedSymbols() {
        return factory.cacheSize();
    }

    public record QuoteRequest(String symbol, double marketPrice, String venue, Optional<String> note) {
        public QuoteRequest {
            note = note == null ? Optional.empty() : note;
        }
    }
}
