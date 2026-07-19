package com.example.prototype;

import com.example.prototype.impl.DefaultPortfolioTemplate;
import com.example.prototype.impl.PortfolioPrototypeRegistry;

import java.util.List;
import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        PortfolioPrototypeRegistry registry = new PortfolioPrototypeRegistry();
        registry.register("growth", new DefaultPortfolioTemplate("Growth Fund", "Aggressive", List.of("ACME", "BLUE"), Optional.of("NASDAQ 100")));

        var cloned = registry.clone("growth");
        cloned.addHolding("GREEN");
        cloned.setBenchmark("MSCI World");

        System.out.println(((DefaultPortfolioTemplate) cloned).render());
        System.out.println(((DefaultPortfolioTemplate) registry.clone("growth")).render());
    }
}
