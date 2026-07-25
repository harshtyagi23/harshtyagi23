package com.example.apigatewaypattern;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "API Gateway pattern baseline path";
            default -> "API Gateway pattern context: " + context.get();
        };
        return new PatternSummary("API Gateway pattern", note);
    }
}
