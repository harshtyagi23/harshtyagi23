package com.example.interpreter.impl;

import com.example.interpreter.api.Expression;

import java.util.Map;

public record OrExpression(Expression left, Expression right) implements Expression {
    @Override
    public boolean interpret(Map<String, Object> context) {
        return left.interpret(context) || right.interpret(context);
    }
}
