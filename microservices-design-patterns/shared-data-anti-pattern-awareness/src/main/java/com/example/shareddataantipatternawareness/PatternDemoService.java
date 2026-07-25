package com.example.shareddataantipatternawareness;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Shared Data Anti-pattern awareness baseline path";
            default -> "Shared Data Anti-pattern awareness context: " + context.get();
        };
        return new PatternSummary("Shared Data Anti-pattern awareness", note);
    }
}
