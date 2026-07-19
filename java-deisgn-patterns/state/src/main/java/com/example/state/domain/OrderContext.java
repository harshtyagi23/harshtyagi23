package com.example.state.domain;

import java.util.Optional;

public record OrderContext(String status, double amount) {
    public String normalizedStatus() {
        return Optional.ofNullable(status)
                .map(String::toLowerCase)
                .orElse("unknown");
    }
}
