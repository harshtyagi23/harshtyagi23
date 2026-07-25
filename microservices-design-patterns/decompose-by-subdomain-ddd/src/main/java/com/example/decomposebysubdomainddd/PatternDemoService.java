package com.example.decomposebysubdomainddd;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Decompose by Subdomain (DDD) baseline path";
            default -> "Decompose by Subdomain (DDD) context: " + context.get();
        };
        return new PatternSummary("Decompose by Subdomain (DDD)", note);
    }
}
