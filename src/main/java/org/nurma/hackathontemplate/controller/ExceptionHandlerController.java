package org.nurma.hackathontemplate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nurma.hackathontemplate.dto.response.CustomErrorResponse;
import org.nurma.hackathontemplate.exception.AuthenticationException;
import org.nurma.hackathontemplate.exception.ResourceNotFoundException;
import org.nurma.hackathontemplate.exception.UnauthorizedAccessException;
import org.nurma.hackathontemplate.exception.ValidationException;
import org.nurma.hackathontemplate.util.ExceptionTitle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class ExceptionHandlerController {
    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthException(final AuthenticationException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ExceptionTitle.AUTHENTICATION,
                e.getMessage()
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {UnauthorizedAccessException.class})
    public ResponseEntity<Object> handleUnauthorizedException(final UnauthorizedAccessException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ExceptionTitle.UNAUTHORIZED_ACCESS,
                e.getMessage()
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class,
            ValidationException.class
    })
    public ResponseEntity<Object> handleValidationException(final Exception e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ExceptionTitle.VALIDATION,
                e.getMessage()
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(final ResourceNotFoundException e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ExceptionTitle.NOT_FOUND,
                e.getMessage()
        );

        return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleException(final Exception e) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                ExceptionTitle.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );

        log.error("!!!Unhandled exception!!!", e);

        return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
