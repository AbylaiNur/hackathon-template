package org.nurma.hackathontemplate.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(final String message) {
        super(message);
    }
}
