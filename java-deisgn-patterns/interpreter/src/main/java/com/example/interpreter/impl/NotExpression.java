package com.example.interpreter.impl;

import com.example.interpreter.api.Expression;

import java.util.Map;

public record NotExpression(Expression expression) implements Expression {
    @Override
    public boolean interpret(Map<String, Object> context) {
        return !expression.interpret(context);
    }
}
