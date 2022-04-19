package com.siretreader.ms.exceptions;

/**
 * Generic exception that is thrown when requested establishment with the given siret number doesn't exist in the local
 * storage (csv file)
 */
public class EstablishmentNotFoundException extends NotFoundException {
    public EstablishmentNotFoundException() {
        super("Establishment not found in the local storage. Try to refresh data via update establishments endpoint");
    }
}
