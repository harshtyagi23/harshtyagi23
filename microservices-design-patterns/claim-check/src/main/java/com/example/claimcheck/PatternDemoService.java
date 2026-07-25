package com.example.claimcheck;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Claim Check baseline path";
            default -> "Claim Check context: " + context.get();
        };
        return new PatternSummary("Claim Check", note);
    }
}
