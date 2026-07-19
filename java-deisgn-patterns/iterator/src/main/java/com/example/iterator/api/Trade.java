package com.example.iterator.api;

import java.util.Optional;

public record Trade(String symbol, double quantity, String side, Optional<String> note) {
    public Trade {
        note = note == null ? Optional.empty() : note;
    }
}
