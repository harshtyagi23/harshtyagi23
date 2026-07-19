package com.example.mediator;

import com.example.mediator.impl.EquityTrader;
import com.example.mediator.impl.FixedIncomeTrader;
import com.example.mediator.impl.TradeDesk;
import com.example.mediator.impl.TradeDeskMediator;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        TradeDesk desk = new TradeDesk(new TradeDeskMediator(), new EquityTrader(), new FixedIncomeTrader());
        System.out.println(desk.equityTrader().requestTrade("ACME", 100).render());
        System.out.println(desk.fixedIncomeTrader().requestTrade("UST10Y", 250).render());
    }
}
