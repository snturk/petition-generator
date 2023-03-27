package org.snturk.petition.signature;

import org.snturk.petition.model.Issuer;

import java.time.LocalDateTime;

/**
 * A model that holds the signature information of a petition.
 * A Petition can be signed by multiple issuers.
 */
public class SignatureInfo {

    private Issuer[] issuers;
    private LocalDateTime signatureDate;

    public SignatureInfo(Issuer[] issuers, LocalDateTime signatureDate) {
        this.issuers = issuers;
        this.signatureDate = signatureDate;
    }

    public Issuer[] getIssuers() {
        return issuers;
    }

    public LocalDateTime getSignatureDate() {
        return signatureDate;
    }
}
