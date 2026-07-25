package com.example.circuitbreaker;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Circuit Breaker baseline path";
            default -> "Circuit Breaker context: " + context.get();
        };
        return new PatternSummary("Circuit Breaker", note);
    }
}
