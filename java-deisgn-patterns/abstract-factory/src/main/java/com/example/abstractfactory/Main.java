package com.example.abstractfactory;

import com.example.abstractfactory.api.AssetClass;
import com.example.abstractfactory.api.MarketDashboardFactory;
import com.example.abstractfactory.api.MarketDashboardFactoryProvider;

import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        AssetClass assetClass = args.length > 0 && "bond".equalsIgnoreCase(args[0])
                ? AssetClass.BOND
                : AssetClass.EQUITY;

        MarketDashboardFactory factory = MarketDashboardFactoryProvider.factoryFor(assetClass);
        var dashboard = factory.createDashboard("ACME", Optional.of(128.75), Optional.of(4));
        System.out.println(dashboard.render());
    }
}
