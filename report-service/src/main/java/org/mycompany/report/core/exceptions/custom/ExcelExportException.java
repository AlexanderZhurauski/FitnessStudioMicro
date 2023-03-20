package org.mycompany.report.core.exceptions.custom;

public class ExcelExportException extends RuntimeException {

    public ExcelExportException(String message) {
        super(message);
    }

    public ExcelExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelExportException(Throwable cause) {
        super(cause);
    }
}
