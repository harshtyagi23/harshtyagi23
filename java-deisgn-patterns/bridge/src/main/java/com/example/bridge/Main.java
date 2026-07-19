package com.example.bridge;

import com.example.bridge.api.Report;
import com.example.bridge.impl.MarkdownRenderer;
import com.example.bridge.impl.PortfolioReport;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        Report report = new PortfolioReport(
                new MarkdownRenderer(),
                "Quarterly Portfolio Review",
                "Equity exposure remains overweight in the growth sleeve.",
                "Approved by portfolio oversight"
        );
        System.out.println(report.publish());
    }
}
