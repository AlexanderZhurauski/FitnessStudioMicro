package org.mycompany.mail.core.exceptions.messages;

public class ErrorField {

    private String message;
    private String field;

    public ErrorField(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
