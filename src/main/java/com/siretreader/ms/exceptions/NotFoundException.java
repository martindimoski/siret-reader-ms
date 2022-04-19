package com.siretreader.ms.exceptions;

/**
 * Generic exception when an entity does not exist
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String s) {
        super(s);
    }
}
