package com.siretreader.ms.exceptions;

/**
 * Generic exception that is thrown when the response status from sirene api is 5xx
 */
public class SireneServerDownException extends RuntimeException {
    public SireneServerDownException() {
        super("Server is down");
    }
}
