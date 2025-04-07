package com.folio.contrucoes.exception;

public class UnauthorizedException extends NoStackTraceException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
