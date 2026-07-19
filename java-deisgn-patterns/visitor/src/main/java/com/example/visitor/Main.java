package com.example.visitor;

import com.example.visitor.api.PortfolioElement;
import com.example.visitor.impl.CashPosition;
import com.example.visitor.impl.Holding;
import com.example.visitor.impl.PortfolioGroup;
import com.example.visitor.impl.ReportVisitor;
import com.example.visitor.impl.ValuationVisitor;

import java.util.List;
import java.util.Optional;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        PortfolioElement portfolio = new PortfolioGroup(
                "Core Portfolio",
                List.of(
                        new Holding("ACME", 100, 12.5, Optional.of("Technology")),
                        new CashPosition(500.0)
                ),
                Optional.of("balanced")
        );

        System.out.println(String.format("Total: %.2f", portfolio.accept(new ValuationVisitor())));
        System.out.println(portfolio.accept(new ReportVisitor()));
    }
}
