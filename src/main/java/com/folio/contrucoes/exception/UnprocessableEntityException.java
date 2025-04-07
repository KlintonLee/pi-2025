package com.folio.contrucoes.exception;

public class UnprocessableEntityException extends NoStackTraceException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
