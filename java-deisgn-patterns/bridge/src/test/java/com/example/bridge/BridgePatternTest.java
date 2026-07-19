package com.example.bridge;

import com.example.bridge.api.Report;
import com.example.bridge.impl.MarkdownRenderer;
import com.example.bridge.impl.PlainTextRenderer;
import com.example.bridge.impl.PortfolioReport;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BridgePatternTest {
    @Test
    void rendersPortfolioReportAsPlainText() {
        Report report = new PortfolioReport(
                new PlainTextRenderer(),
                "Quarterly Portfolio Review",
                "Equity exposure remains overweight in the growth sleeve.",
                "Approved by portfolio oversight"
        );

        assertEquals(
                "Title: Quarterly Portfolio Review\n" +
                "Body: Equity exposure remains overweight in the growth sleeve.\n" +
                "Note: Approved by portfolio oversight\n",
                report.publish()
        );
    }

    @Test
    void rendersPortfolioReportAsMarkdown() {
        Report report = new PortfolioReport(
                new MarkdownRenderer(),
                "Quarterly Portfolio Review",
                "Equity exposure remains overweight in the growth sleeve.",
                "Approved by portfolio oversight"
        );

        assertEquals(
                "# Quarterly Portfolio Review\n" +
                "Equity exposure remains overweight in the growth sleeve.\n\n" +
                "> Approved by portfolio oversight\n",
                report.publish()
        );
    }
}
