package com.example.proxy.impl;

import com.example.proxy.api.TradingAccountService;

import java.util.Optional;

public class SecureTradingAccountProxy implements TradingAccountService {
    private final TradingAccountService delegate;

    public SecureTradingAccountProxy(TradingAccountService delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getAccountSummary(String accountId) {
        return Optional.ofNullable(accountId)
                .map(String::trim)
                .filter(value -> !value.isBlank())
                .map(this::checkAccess)
                .orElse("UNKNOWN|DENIED|0.00");
    }

    private String checkAccess(String accountId) {
        String accessLevel = switch (accountId.toLowerCase()) {
            case "acct-001" -> "GRANTED";
            case "acct-002" -> "GRANTED";
            default -> "DENIED";
        };
        return accessLevel.equals("GRANTED")
                ? delegate.getAccountSummary(accountId)
                : "UNKNOWN|DENIED|0.00";
    }
}
