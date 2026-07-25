package com.example.logaggregation;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Log Aggregation baseline path";
            default -> "Log Aggregation context: " + context.get();
        };
        return new PatternSummary("Log Aggregation", note);
    }
}
