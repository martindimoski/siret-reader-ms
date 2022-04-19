package com.siretreader.ms.exceptions;

/**
 * Generic exception that is thrown when the response status from sirene api for requested siret number is 404
 */
public class SirenEstablishmentNotFoundException extends NotFoundException {
    public SirenEstablishmentNotFoundException() {
        super("Establishment not found on SIREN");
    }
}
