package com.example.interpreter.impl;

import com.example.interpreter.api.Expression;

import java.util.Map;

public record EqualsExpression(String field, String expected) implements Expression {
    @Override
    public boolean interpret(Map<String, Object> context) {
        Object value = context.get(field);
        return expected.equalsIgnoreCase(String.valueOf(value));
    }
}
