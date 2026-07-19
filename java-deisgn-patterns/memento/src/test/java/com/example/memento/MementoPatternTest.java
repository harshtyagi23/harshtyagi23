package com.example.memento;

import com.example.memento.impl.Portfolio;
import com.example.memento.impl.PortfolioCaretaker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MementoPatternTest {
    @Test
    void restoresAPreviousPortfolioSnapshot() {
        Portfolio portfolio = new Portfolio("Growth Fund", 10000.0);
        PortfolioCaretaker caretaker = new PortfolioCaretaker();

        caretaker.push(portfolio.save());
        portfolio.addHolding("ACME");
        portfolio.adjustCash(-1250.0);

        assertTrue(portfolio.primaryHolding().isPresent());
        assertEquals("Growth Fund cash=8750.00 holdings=[ACME]", portfolio.render());

        portfolio.restore(caretaker.pop());

        assertFalse(portfolio.primaryHolding().isPresent());
        assertEquals("Growth Fund cash=10000.00 holdings=[]", portfolio.render());
    }
}
