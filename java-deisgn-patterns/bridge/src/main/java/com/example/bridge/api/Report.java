package com.example.bridge.api;

public abstract class Report {
    private final ReportRenderer renderer;
    private final String title;
    private final String body;
    private final String note;

    protected Report(ReportRenderer renderer, String title, String body, String note) {
        this.renderer = renderer;
        this.title = title;
        this.body = body;
        this.note = note;
    }

    public String publish() {
        return renderer.renderHeader(title)
                + renderer.renderBody(body)
                + renderer.renderFooter(note);
    }
}
