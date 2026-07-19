package com.example.facade;

import com.example.facade.api.TradeConfirmation;
import com.example.facade.impl.DefaultTradeFacade;
import com.example.facade.impl.ExecutionService;
import com.example.facade.impl.MarketDataService;
import com.example.facade.impl.RiskService;

import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        DefaultTradeFacade facade = new DefaultTradeFacade(new MarketDataService(), new RiskService(), new ExecutionService());
        TradeConfirmation confirmation = facade.executeTrade("ACME", 200, Optional.empty());
        System.out.println(confirmation.render());
    }
}
