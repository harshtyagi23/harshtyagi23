package com.example.interpreter.impl;

import com.example.interpreter.api.Expression;
import com.example.interpreter.api.RuleParser;

import java.util.Locale;

public final class RuleParserImpl implements RuleParser {
    @Override
    public Expression parse(String rule) {
        String normalized = rule.trim();
        String[] orParts = normalized.split("\\s+OR\\s+");
        if (orParts.length > 1) {
            Expression left = parse(orParts[0]);
            Expression right = parse(orParts[1]);
            return new OrExpression(left, right);
        }

        String[] andParts = normalized.split("\\s+AND\\s+");
        if (andParts.length > 1) {
            Expression left = parse(andParts[0]);
            Expression right = parse(andParts[1]);
            return new AndExpression(left, right);
        }

        if (normalized.toUpperCase(Locale.ROOT).startsWith("NOT ")) {
            return new NotExpression(parse(normalized.substring(4)));
        }

        return parseAtomic(normalized);
    }

    private Expression parseAtomic(String rule) {
        String[] parts;
        if ((parts = rule.split("\\s+>=\\s+")).length == 2) {
            return new ComparisonExpression(parts[0].trim(), ComparisonOperator.GTE, Double.parseDouble(parts[1].trim()));
        }
        if ((parts = rule.split("\\s+<=\\s+")).length == 2) {
            return new ComparisonExpression(parts[0].trim(), ComparisonOperator.LTE, Double.parseDouble(parts[1].trim()));
        }
        if ((parts = rule.split("\\s+>\\s+")).length == 2) {
            return new ComparisonExpression(parts[0].trim(), ComparisonOperator.GT, Double.parseDouble(parts[1].trim()));
        }
        if ((parts = rule.split("\\s+<\\s+")).length == 2) {
            return new ComparisonExpression(parts[0].trim(), ComparisonOperator.LT, Double.parseDouble(parts[1].trim()));
        }
        if ((parts = rule.split("\\s*=\\s+")).length == 2) {
            String right = parts[1].trim();
            if (right.matches("-?\\d+(\\.\\d+)?")) {
                return new ComparisonExpression(parts[0].trim(), ComparisonOperator.EQ, Double.parseDouble(right));
            }
            return new EqualsExpression(parts[0].trim(), right);
        }
        throw new IllegalArgumentException("Unable to parse rule: " + rule);
    }
}
