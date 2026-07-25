package com.example.asynchronousmessaging;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Asynchronous Messaging baseline path";
            default -> "Asynchronous Messaging context: " + context.get();
        };
        return new PatternSummary("Asynchronous Messaging", note);
    }
}
