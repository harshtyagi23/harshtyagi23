package com.example.externalizedconfiguration;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Externalized Configuration baseline path";
            default -> "Externalized Configuration context: " + context.get();
        };
        return new PatternSummary("Externalized Configuration", note);
    }
}
