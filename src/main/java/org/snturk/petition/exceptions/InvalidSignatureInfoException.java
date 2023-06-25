package org.snturk.petition.exceptions;

/**
 * InvalidSignatureInfoException is thrown when a signature info is not valid.
 */
public class InvalidSignatureInfoException extends IllegalArgumentException {

    /**
     * Constructor of InvalidSignatureInfoException
     * @param message Message of the exception
     */
    public InvalidSignatureInfoException(String message) {
        super(message);
    }
}
