package com.example.shareddataantipatternawareness;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class PatternDemoServiceTest {

    @Test
    void returnsPatternSummary() {
        PatternDemoService service = new PatternDemoService();
        PatternDemoService.PatternSummary summary = service.summarize(Optional.of("sample"));
        assertEquals("Shared Data Anti-pattern awareness", summary.pattern());
    }
}
