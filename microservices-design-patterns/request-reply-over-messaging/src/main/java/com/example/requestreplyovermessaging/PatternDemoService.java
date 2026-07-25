package com.example.requestreplyovermessaging;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Request-Reply over Messaging baseline path";
            default -> "Request-Reply over Messaging context: " + context.get();
        };
        return new PatternSummary("Request-Reply over Messaging", note);
    }
}
