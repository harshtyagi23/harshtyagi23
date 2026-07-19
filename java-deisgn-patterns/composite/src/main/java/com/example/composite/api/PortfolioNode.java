package com.example.composite.api;

import java.util.List;

public interface PortfolioNode extends PortfolioComponent {
    List<PortfolioComponent> children();

    default boolean isLeaf() {
        return children().isEmpty();
    }
}
