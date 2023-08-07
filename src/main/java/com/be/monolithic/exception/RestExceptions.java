package com.be.monolithic.exception;

import org.springframework.http.HttpStatus;

public class RestExceptions {
    public static class InternalServerError extends BaseException {
        public InternalServerError(String msgKey) {
            super(msgKey);
            setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public static class BadRequest extends BaseException {
        public BadRequest(String msgKey) {
            super(msgKey);
            setStatus(HttpStatus.BAD_REQUEST);
        }
    }

    public static class NotImplemented extends BaseException {
        public NotImplemented() {
            super(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
            setStatus(HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
