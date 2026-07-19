package com.example.interpreter;

import com.example.interpreter.impl.RuleParserImpl;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InterpreterPatternTest {
    @Test
    void interpretsAndRuleAgainstPortfolioContext() {
        RuleParserImpl parser = new RuleParserImpl();
        var rule = parser.parse("sector = Technology AND value > 1000");

        assertTrue(rule.interpret(Map.of(
                "sector", "Technology",
                "value", 1250.0
        )));
        assertFalse(rule.interpret(Map.of(
                "sector", "Technology",
                "value", 850.0
        )));
    }

    @Test
    void interpretsOrAndNotRules() {
        RuleParserImpl parser = new RuleParserImpl();
        var rule = parser.parse("NOT sector = Energy OR value >= 500");

        assertTrue(rule.interpret(Map.of(
                "sector", "Technology",
                "value", 100.0
        )));
        assertFalse(rule.interpret(Map.of(
                "sector", "Energy",
                "value", 100.0
        )));
    }
}
