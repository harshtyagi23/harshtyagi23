package com.example.mediator.impl;

import com.example.mediator.api.TradeColleague;
import com.example.mediator.api.TradeDecision;
import com.example.mediator.api.TradeMediator;

public final class EquityTrader implements TradeColleague {
    private TradeMediator mediator;

    @Override
    public String desk() {
        return "equity";
    }

    @Override
    public void setMediator(TradeMediator mediator) {
        this.mediator = mediator;
    }

    public TradeDecision requestTrade(String symbol, double quantity) {
        return mediator.submitTrade(desk(), symbol, quantity, "BUY");
    }
}
