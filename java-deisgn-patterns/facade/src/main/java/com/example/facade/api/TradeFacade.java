package com.example.facade.api;

import java.util.Optional;

public interface TradeFacade {
    TradeConfirmation executeTrade(String symbol, double quantity, Optional<Double> limitPrice);
}
