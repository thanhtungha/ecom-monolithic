package com.be.monolithic.exception;

import org.springframework.http.HttpStatus;

public class RestExceptions {

    public static class BadRequest extends BaseException {
        public BadRequest(String msgKey) {
            super(msgKey);
            setStatus(HttpStatus.BAD_REQUEST);
        }
    }

    public static class NotImplemented extends BaseException {
        public NotImplemented() {
            super("Service not implemented");
            setStatus(HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
