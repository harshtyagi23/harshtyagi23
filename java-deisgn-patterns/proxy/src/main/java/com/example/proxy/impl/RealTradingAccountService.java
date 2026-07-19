package com.example.proxy.impl;

import com.example.proxy.api.TradingAccountService;
import com.example.proxy.domain.AccountDetails;

public class RealTradingAccountService implements TradingAccountService {
    @Override
    public String getAccountSummary(String accountId) {
        AccountDetails details = new AccountDetails(accountId, "PREMIUM", 250000.0);
        return "%s|%s|%.2f".formatted(details.accountId(), details.tier(), details.balance());
    }
}
