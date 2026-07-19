package com.example.abstractfactory.api;

import com.example.abstractfactory.impl.BondMarketDashboardFactory;
import com.example.abstractfactory.impl.EquityMarketDashboardFactory;

public final class MarketDashboardFactoryProvider {
    private MarketDashboardFactoryProvider() {
    }

    public static MarketDashboardFactory factoryFor(AssetClass assetClass) {
        return switch (assetClass) {
            case EQUITY -> new EquityMarketDashboardFactory();
            case BOND -> new BondMarketDashboardFactory();
        };
    }
}
