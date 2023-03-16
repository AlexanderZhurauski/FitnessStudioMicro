package org.mycompany.audit.core.exceptions.custom;

public class NoValidTokenFound extends RuntimeException {

    public NoValidTokenFound(String message) {
        super(message);
    }
}
