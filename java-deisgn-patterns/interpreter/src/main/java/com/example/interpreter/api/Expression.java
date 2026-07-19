package com.example.interpreter.api;

import java.util.Map;

public interface Expression {
    boolean interpret(Map<String, Object> context);
}
