package com.example.bridge.impl;

import com.example.bridge.api.ReportRenderer;

public final class PlainTextRenderer implements ReportRenderer {
    @Override
    public String renderHeader(String title) {
        return "Title: " + title + System.lineSeparator();
    }

    @Override
    public String renderBody(String body) {
        return "Body: " + body + System.lineSeparator();
    }

    @Override
    public String renderFooter(String note) {
        return "Note: " + note + System.lineSeparator();
    }
}
