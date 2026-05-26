package com.cool.spring.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setTitle("Entity not found");
        pd.setDetail(ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pd.setTitle("Internal server error");
        pd.setDetail("Unexpected error");
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

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
    public ProblemDetail handleConstraintViolation(
            ConstraintViolationException ex
    ) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        pd.setTitle("Constraint violation");

        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();

        pd.setProperty("errors", errors);

        return pd;
    }

}
