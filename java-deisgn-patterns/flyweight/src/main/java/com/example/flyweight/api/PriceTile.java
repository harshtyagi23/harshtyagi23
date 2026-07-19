package com.example.flyweight.api;

public interface PriceTile {
    String symbol();

    String render(double marketPrice, String venue);
}
