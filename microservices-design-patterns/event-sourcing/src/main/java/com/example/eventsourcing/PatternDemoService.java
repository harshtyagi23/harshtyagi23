package com.example.eventsourcing;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Event Sourcing baseline path";
            default -> "Event Sourcing context: " + context.get();
        };
        return new PatternSummary("Event Sourcing", note);
    }
}
