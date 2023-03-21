package org.mycompany.report.core.exceptions.messages;

public class ErrorField {

    private String message;
    private String field;

    public ErrorField(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return this.message;
    }

    public String getField() {
        return this.field;
    }
}
