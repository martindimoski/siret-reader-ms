package com.siretreader.ms.exceptions;

/**
 *  Generic exception that is thrown when the establishments csv file doesn't exist in the user folder
 */
public class CSVFileNotFoundException extends NotFoundException {
    public CSVFileNotFoundException() {
        super("The establishments not found cause they have not been updated yet. Try to update them first.");
    }
}
