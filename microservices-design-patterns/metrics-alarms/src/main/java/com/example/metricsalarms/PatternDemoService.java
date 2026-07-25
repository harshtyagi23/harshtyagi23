package com.example.metricsalarms;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Metrics & Alarms baseline path";
            default -> "Metrics & Alarms context: " + context.get();
        };
        return new PatternSummary("Metrics & Alarms", note);
    }
}
