package com.example.mediator.api;

public record TradeDecision(boolean approved, String message, String route) {
    public String render() {
        return (approved ? "APPROVED" : "REJECTED") + " " + route + " - " + message;
    }
}
