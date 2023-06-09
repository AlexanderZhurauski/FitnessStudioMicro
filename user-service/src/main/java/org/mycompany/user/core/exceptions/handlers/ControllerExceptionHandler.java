package org.mycompany.user.core.exceptions.handlers;

import feign.FeignException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.DataException;
import org.mycompany.user.core.exceptions.custom.EntityNotFoundException;
import org.mycompany.user.core.exceptions.custom.ExcelExportException;
import org.mycompany.user.core.exceptions.custom.NoValidTokenFound;
import org.mycompany.user.core.exceptions.messages.ErrorField;
import org.mycompany.user.core.exceptions.messages.MultipleErrorResponse;
import org.mycompany.user.core.exceptions.messages.SingleErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private static final String ERROR = "error";
    private static final String STRUCTURED_ERROR = "structured error";
    private static final String PATH_VARIABLE_EX = "The required path variable '%s' is missing from the request URL.";
    private static final String REQUEST_BODY_NOT_READABLE = "The request body is not readable, is missing required fields or has invalid data.";
    private static final String METHOD_ARGUMENT_MISMATCH = "The value '%s' is not appropriate for type '%s'";
    private static final String AUTHENTICATION_FAILED = "Authentication has failed! Please check the validity of your input data";
    private static final String INVALID_ARGUMENT = "The argument passed to the method is invalid";
    private static final String CONSTRAINT_VIOLATION = "One or more constraints have been violated!";
    private static final String SERVICE_COMMUNICATION_ERROR = "There has been an error in communication between services.";
    private static final String DATABASE_ERROR = "There has been an error in the database layer. Please contact the administrator!";
    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({NoValidTokenFound.class, BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<List<SingleErrorResponse>> handleAuthException(RuntimeException ex) {
        SingleErrorResponse errorResponse = new SingleErrorResponse();
        errorResponse.setLogref(ERROR);

        if (ex instanceof NoValidTokenFound || ex instanceof BadCredentialsException) {
            errorResponse.setMessage(ex.getMessage());
            this.logger.error(ex.getMessage(), ex);
        } else {
            errorResponse.setMessage(AUTHENTICATION_FAILED);
            this.logger.error(AUTHENTICATION_FAILED, ex);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(List.of(errorResponse));
    }
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<List<SingleErrorResponse>> handleMissingPathVariableException(
            MissingPathVariableException ex) {

        String message = String.format(PATH_VARIABLE_EX, ex.getVariableName());
        SingleErrorResponse errorResponse = new SingleErrorResponse(ERROR, message);
        this.logger.error(PATH_VARIABLE_EX, ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(errorResponse));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<SingleErrorResponse>> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        String message = String.format(METHOD_ARGUMENT_MISMATCH, ex.getValue(),
                ex.getRequiredType(), ex.getName());
        SingleErrorResponse errorResponse = new SingleErrorResponse(ERROR, message);
        this.logger.error(message, ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(errorResponse));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<SingleErrorResponse>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        SingleErrorResponse errorResponse = new SingleErrorResponse(ERROR,
                REQUEST_BODY_NOT_READABLE);
        this.logger.error(REQUEST_BODY_NOT_READABLE, ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of(errorResponse));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MultipleErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {

        MultipleErrorResponse errorResponse = new MultipleErrorResponse();
        List<ErrorField> errorFields = ex.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorField(violation.getMessage(),
                        violation.getPropertyPath().toString()))
                .collect(Collectors.toList());

        errorResponse.setLogref(STRUCTURED_ERROR);
        errorResponse.setErrors(errorFields);
        this.logger.error(CONSTRAINT_VIOLATION, ex);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultipleErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        MultipleErrorResponse errorResponse = new MultipleErrorResponse();
        List<ErrorField> errorFields = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorField(fieldError.getDefaultMessage(),
                        fieldError.getField()))
                .collect(Collectors.toList());

        errorResponse.setLogref(STRUCTURED_ERROR);
        errorResponse.setErrors(errorFields);
        this.logger.error(INVALID_ARGUMENT, ex);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({EntityNotFoundException.class, OptimisticLockException.class,
            SQLException.class, PropertyValueException.class, DataException.class})
    public ResponseEntity<List<SingleErrorResponse>> handlePersistenceLayerException(RuntimeException ex) {
        SingleErrorResponse errorResponse = new SingleErrorResponse();
        errorResponse.setLogref(ERROR);

        if (ex instanceof EntityNotFoundException || ex instanceof PropertyValueException) {
            errorResponse.setMessage(ex.getMessage());
            this.logger.error(DATABASE_ERROR, ex);
            return ResponseEntity.badRequest().body(List.of(errorResponse));
        }

        errorResponse.setMessage(DATABASE_ERROR);
        this.logger.error(DATABASE_ERROR, ex);

        return ResponseEntity.internalServerError().body(List.of(errorResponse));
    }

    @ExceptionHandler({FeignException.class})
    public ResponseEntity<List<SingleErrorResponse>> handleFeignException(RuntimeException ex) {
        SingleErrorResponse errorResponse = new SingleErrorResponse();
        errorResponse.setLogref(ERROR);
        errorResponse.setMessage(SERVICE_COMMUNICATION_ERROR);
        this.logger.error(SERVICE_COMMUNICATION_ERROR, ex);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(List.of(errorResponse));
    }

    @ExceptionHandler({NullPointerException.class, ExcelExportException.class, IllegalArgumentException.class,
            RuntimeException.class})
    public ResponseEntity<List<SingleErrorResponse>> handleGeneralServiceException(RuntimeException ex) {
        SingleErrorResponse errorResponse = new SingleErrorResponse();
        Throwable rootCause = NestedExceptionUtils.getMostSpecificCause(ex);
        errorResponse.setLogref(ERROR);
        String message = rootCause.getMessage();
        errorResponse.setMessage(message);
        this.logger.error(message, ex);

        return ResponseEntity.internalServerError().body(List.of(errorResponse));
    }
}
