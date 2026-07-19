package com.example.decorator.application;

import com.example.decorator.api.PortfolioReport;

public abstract class PortfolioReportDecorator implements PortfolioReport {
    protected final PortfolioReport delegate;

    protected PortfolioReportDecorator(PortfolioReport delegate) {
        this.delegate = delegate;
    }
}
