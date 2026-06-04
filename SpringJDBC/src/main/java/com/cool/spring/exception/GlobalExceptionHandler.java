package com.cool.spring.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail handleEntityNotFound(
            BookNotFoundException ex,
            HttpServletRequest request
    ) {

        ProblemDetail pd =
                ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pd.setTitle("Book not found");
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
        pd.setDetail("Unexpected error: " + ex.getMessage());
        pd.setProperty("path", request.getRequestURI());

        return pd;
    }

}
