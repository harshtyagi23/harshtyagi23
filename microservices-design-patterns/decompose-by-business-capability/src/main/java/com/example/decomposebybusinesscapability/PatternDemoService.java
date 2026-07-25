package com.example.decomposebybusinesscapability;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Decompose by Business Capability baseline path";
            default -> "Decompose by Business Capability context: " + context.get();
        };
        return new PatternSummary("Decompose by Business Capability", note);
    }
}
