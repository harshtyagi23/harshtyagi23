package com.example.retrywithexponentialbackoffjitter;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Retry with Exponential Backoff + Jitter baseline path";
            default -> "Retry with Exponential Backoff + Jitter context: " + context.get();
        };
        return new PatternSummary("Retry with Exponential Backoff + Jitter", note);
    }
}
