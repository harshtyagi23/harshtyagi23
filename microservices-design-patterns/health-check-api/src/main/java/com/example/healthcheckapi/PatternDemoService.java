package com.example.healthcheckapi;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Health Check API baseline path";
            default -> "Health Check API context: " + context.get();
        };
        return new PatternSummary("Health Check API", note);
    }
}
