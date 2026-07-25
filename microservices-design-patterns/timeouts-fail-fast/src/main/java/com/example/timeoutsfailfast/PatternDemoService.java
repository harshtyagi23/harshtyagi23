package com.example.timeoutsfailfast;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PatternDemoService {

    public record PatternSummary(String pattern, String note) {}

    public PatternSummary summarize(Optional<String> context) {
        String note = switch (context.orElse("default")) {
            case "default" -> "Timeouts & Fail Fast baseline path";
            default -> "Timeouts & Fail Fast context: " + context.get();
        };
        return new PatternSummary("Timeouts & Fail Fast", note);
    }
}
