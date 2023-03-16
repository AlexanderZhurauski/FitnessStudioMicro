package org.mycompany.audit.core.exceptions.messages;

import java.util.List;

public class MultipleErrorResponse {

    private String logref;
    private List<ErrorField> errors;

    public MultipleErrorResponse() {
    }

    public MultipleErrorResponse(String logref, List<ErrorField> errors) {
        this.logref = logref;
        this.errors = errors;
    }

    public String getLogref() {
        return logref;
    }

    public void setLogref(String logref) {
        this.logref = logref;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorField> errors) {
        this.errors = errors;
    }
}
