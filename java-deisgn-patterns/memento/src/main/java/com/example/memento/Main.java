package com.example.memento;

import com.example.memento.impl.Portfolio;
import com.example.memento.impl.PortfolioCaretaker;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Portfolio portfolio = new Portfolio("Growth Fund", 10000.0);
        PortfolioCaretaker caretaker = new PortfolioCaretaker();

        caretaker.push(portfolio.save());
        portfolio.addHolding("ACME");
        portfolio.adjustCash(-1250.0);
        System.out.println(portfolio.render());

        portfolio.restore(caretaker.pop());
        System.out.println(portfolio.render());
    }
}
