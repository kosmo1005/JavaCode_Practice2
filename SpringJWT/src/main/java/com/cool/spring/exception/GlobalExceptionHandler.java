package com.cool.spring.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest request) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setTitle("Entity not found");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Illegal Argument");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Bad Credentials");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(DisabledException.class)
    public ProblemDetail handleDisabled(DisabledException ex, HttpServletRequest request) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        pd.setTitle("Disabled");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled exception", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pd.setTitle("Internal server error");
        pd.setDetail(ex.getMessage());
        pd.setProperty("exception", ex.getClass().getSimpleName());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Validation failed");

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        pd.setProperty("errors", errors);

        return pd;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Constraint violation");

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        pd.setProperty("errors", errors);

        return pd;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {

        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        pd.setTitle("Forbidden");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

}
