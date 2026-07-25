package com.example.messagebrokereventbus;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Message Broker / Event Bus baseline path";
            default -> "Message Broker / Event Bus context: " + context.get();
        };
        return new PatternSummary("Message Broker / Event Bus", note);
    }
}
