package com.example.databaseperservice;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Database per Service baseline path";
            default -> "Database per Service context: " + context.get();
        };
        return new PatternSummary("Database per Service", note);
    }
}
