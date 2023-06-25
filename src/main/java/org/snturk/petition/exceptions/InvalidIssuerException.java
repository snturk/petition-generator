package org.snturk.petition.exceptions;

/**
 * InvalidIssuerException is thrown when an issuer is not valid.
 */
public class InvalidIssuerException extends IllegalArgumentException {

    /**
     * Constructor of InvalidIssuerException
     * @param s Message of the exception
     */
    public InvalidIssuerException(String s) {
        super(s);
    }
}
