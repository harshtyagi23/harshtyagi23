package com.example.interpreter;

import com.example.interpreter.api.Expression;
import com.example.interpreter.impl.RuleParserImpl;

import java.util.Map;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        RuleParserImpl parser = new RuleParserImpl();
        Expression rule = parser.parse("sector = Technology AND value > 1000");
        boolean matches = rule.interpret(Map.of(
                "sector", "Technology",
                "value", 1250.0
        ));
        System.out.println(matches);
    }
}
