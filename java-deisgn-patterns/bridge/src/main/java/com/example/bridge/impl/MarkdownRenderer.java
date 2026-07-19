package com.example.bridge.impl;

import com.example.bridge.api.ReportRenderer;

public final class MarkdownRenderer implements ReportRenderer {
    @Override
    public String renderHeader(String title) {
        return "# " + title + System.lineSeparator();
    }

    @Override
    public String renderBody(String body) {
        return body + System.lineSeparator() + System.lineSeparator();
    }

    @Override
    public String renderFooter(String note) {
        return "> " + note + System.lineSeparator();
    }
}
