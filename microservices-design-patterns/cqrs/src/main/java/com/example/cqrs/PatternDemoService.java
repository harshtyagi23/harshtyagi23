package com.example.cqrs;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "CQRS baseline path";
            default -> "CQRS context: " + context.get();
        };
        return new PatternSummary("CQRS", note);
    }
}
