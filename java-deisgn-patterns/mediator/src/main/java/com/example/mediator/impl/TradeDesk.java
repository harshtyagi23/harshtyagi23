package com.example.mediator.impl;

import com.example.mediator.api.TradeColleague;
import com.example.mediator.api.TradeMediator;

public final class TradeDesk {
    private final TradeMediator mediator;
    private final EquityTrader equityTrader;
    private final FixedIncomeTrader fixedIncomeTrader;

    public TradeDesk(TradeMediator mediator, EquityTrader equityTrader, FixedIncomeTrader fixedIncomeTrader) {
        this.mediator = mediator;
        this.equityTrader = equityTrader;
        this.fixedIncomeTrader = fixedIncomeTrader;
        this.equityTrader.setMediator(mediator);
        this.fixedIncomeTrader.setMediator(mediator);
    }

    public TradeMediator mediator() {
        return mediator;
    }

    public EquityTrader equityTrader() {
        return equityTrader;
    }

    public FixedIncomeTrader fixedIncomeTrader() {
        return fixedIncomeTrader;
    }
}
