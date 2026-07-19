package com.example.bridge.api;

public interface ReportRenderer {
    String renderHeader(String title);

    String renderBody(String body);

    String renderFooter(String note);
}
