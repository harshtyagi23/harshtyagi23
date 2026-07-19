package com.example.proxy;

import com.example.proxy.impl.RealTradingAccountService;
import com.example.proxy.impl.SecureTradingAccountProxy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProxyPatternTest {

    @Test
    void shouldAllowGrantedAccountsThroughProxy() {
        SecureTradingAccountProxy proxy = new SecureTradingAccountProxy(new RealTradingAccountService());

        String summary = proxy.getAccountSummary("acct-001");

        assertEquals("acct-001|PREMIUM|250000.00", summary);
    }

    @Test
    void shouldDenyUnknownAccounts() {
        SecureTradingAccountProxy proxy = new SecureTradingAccountProxy(new RealTradingAccountService());

        String summary = proxy.getAccountSummary("acct-999");

        assertEquals("UNKNOWN|DENIED|0.00", summary);
    }
}
