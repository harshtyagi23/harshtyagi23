package com.example.transactionaloutbox;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Transactional Outbox baseline path";
            default -> "Transactional Outbox context: " + context.get();
        };
        return new PatternSummary("Transactional Outbox", note);
    }
}
