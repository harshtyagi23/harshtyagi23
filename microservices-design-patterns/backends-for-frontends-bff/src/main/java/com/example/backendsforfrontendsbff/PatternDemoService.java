package com.example.backendsforfrontendsbff;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Backends for Frontends (BFF) baseline path";
            default -> "Backends for Frontends (BFF) context: " + context.get();
        };
        return new PatternSummary("Backends for Frontends (BFF)", note);
    }
}
