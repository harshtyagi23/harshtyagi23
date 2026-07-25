package com.example.idempotentconsumer;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Idempotent Consumer baseline path";
            default -> "Idempotent Consumer context: " + context.get();
        };
        return new PatternSummary("Idempotent Consumer", note);
    }
}
