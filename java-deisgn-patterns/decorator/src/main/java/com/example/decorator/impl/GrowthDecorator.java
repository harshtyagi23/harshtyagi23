package com.example.decorator.impl;

import com.example.decorator.api.PortfolioReport;
import com.example.decorator.application.PortfolioReportDecorator;

public class GrowthDecorator extends PortfolioReportDecorator {
    public GrowthDecorator(PortfolioReport delegate) {
        super(delegate);
    }

    @Override
    public String render() {
        return delegate.render() + " with growth outlook";
    }
}
