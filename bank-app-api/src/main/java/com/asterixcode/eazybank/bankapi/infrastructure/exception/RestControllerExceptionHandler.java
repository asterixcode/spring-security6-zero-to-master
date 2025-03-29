package com.asterixcode.eazybank.bankapi.infrastructure.exception;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  ResponseEntity<ProblemDetail> handleGenericException(
      final Exception ex, final HttpServletRequest request) {
    log.error("An unexpected error occurred: {}", ex.getMessage());
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(
            createProblemDetail(
                INTERNAL_SERVER_ERROR, ex.getMessage(), "An unexpected error occurred", request));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<ProblemDetail> handleIllegalArgumentException(
      final IllegalArgumentException ex, final HttpServletRequest request) {
    log.error("IllegalArgumentException: {}", ex.getMessage());
    return ResponseEntity.status(BAD_REQUEST)
        .body(createProblemDetail(BAD_REQUEST, ex.getMessage(), "Bad request", request));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex, final HttpServletRequest request) {
    log.error("Validation error: {}", ex.getMessage());
    ProblemDetail problemDetail =
        createProblemDetail(
            BAD_REQUEST,
            ex.getMessage(),
            "Validation Error, Exception in validation fields",
            request);
    List<String> errors = new ArrayList<>();
    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
    }
    problemDetail.setProperty("errors", errors);
    return ResponseEntity.badRequest().body(problemDetail);
  }

  private ProblemDetail createProblemDetail(
      HttpStatus status, String exceptionMessage, String title, HttpServletRequest request) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exceptionMessage);
    problemDetail.setTitle(title);
    problemDetail.setProperty("timestamp", now());
    problemDetail.setProperty("path", request.getRequestURI());
    return problemDetail;
  }
}
