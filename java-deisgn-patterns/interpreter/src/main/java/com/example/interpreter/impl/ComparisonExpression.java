package com.example.interpreter.impl;

import com.example.interpreter.api.Expression;

import java.util.Map;

public record ComparisonExpression(String field, ComparisonOperator operator, double threshold) implements Expression {
    @Override
    public boolean interpret(Map<String, Object> context) {
        Object value = context.get(field);
        if (!(value instanceof Number number)) {
            return false;
        }
        double actual = number.doubleValue();
        return switch (operator) {
            case GT -> actual > threshold;
            case GTE -> actual >= threshold;
            case LT -> actual < threshold;
            case LTE -> actual <= threshold;
            case EQ -> actual == threshold;
        };
    }
}
