package com.example.stranglerfig;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Strangler Fig baseline path";
            default -> "Strangler Fig context: " + context.get();
        };
        return new PatternSummary("Strangler Fig", note);
    }
}
