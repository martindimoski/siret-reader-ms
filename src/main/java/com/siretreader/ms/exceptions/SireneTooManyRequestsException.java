package com.siretreader.ms.exceptions;

public class SireneTooManyRequestsException extends RuntimeException {
    public SireneTooManyRequestsException() {
        super("The maximum call volume was exceeded");
    }
}
