package com.example.flyweight;

import com.example.flyweight.api.PriceTileFactory;
import com.example.flyweight.impl.QuoteBoard;

import java.util.List;
import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        QuoteBoard board = new QuoteBoard(new PriceTileFactory());
        var quotes = board.renderQuotes(List.of(
                new QuoteBoard.QuoteRequest("ACME", 128.75, "NYSE", Optional.of("primary")),
                new QuoteBoard.QuoteRequest("ACME", 129.10, "NYSE", Optional.empty()),
                new QuoteBoard.QuoteRequest("BLUE", 11.20, "LSE", Optional.empty())
        ));

        quotes.forEach(System.out::println);
    }
}
