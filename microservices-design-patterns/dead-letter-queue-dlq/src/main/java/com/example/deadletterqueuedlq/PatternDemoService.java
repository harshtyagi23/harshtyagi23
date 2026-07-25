package com.example.deadletterqueuedlq;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Dead Letter Queue (DLQ) baseline path";
            default -> "Dead Letter Queue (DLQ) context: " + context.get();
        };
        return new PatternSummary("Dead Letter Queue (DLQ)", note);
    }
}
