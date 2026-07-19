package com.example.decorator.impl;

import com.example.decorator.api.PortfolioReport;
import com.example.decorator.application.PortfolioReportDecorator;

public class CostsDecorator extends PortfolioReportDecorator {
    public CostsDecorator(PortfolioReport delegate) {
        super(delegate);
    }

    @Override
    public String render() {
        return delegate.render() + " and cost analysis";
    }
}
