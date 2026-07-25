package com.example.servicepercontainerserverlessfunction;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Service per Container / Serverless Function baseline path";
            default -> "Service per Container / Serverless Function context: " + context.get();
        };
        return new PatternSummary("Service per Container / Serverless Function", note);
    }
}
