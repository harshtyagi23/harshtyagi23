package com.example.sagaorchestrationchoreography;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Saga (Orchestration & Choreography) baseline path";
            default -> "Saga (Orchestration & Choreography) context: " + context.get();
        };
        return new PatternSummary("Saga (Orchestration & Choreography)", note);
    }
}
