package com.example.bridge.impl;

import com.example.bridge.api.Report;
import com.example.bridge.api.ReportRenderer;

public final class PortfolioReport extends Report {
    public PortfolioReport(ReportRenderer renderer, String title, String body, String note) {
        super(renderer, title, body, note);
    }
}
