package org.snturk.petition.model;

/**
 * Issuer or signer of the petition, it can be a person or an organization.
 * Only classes that implement Issuer can sign a petition.
 */
public interface Issuer {

    /**
     * Name of the issuer, can be Name Surname or Organization Name
     * @return complete name of the issuer
     */
    String getCompleteName();
}
