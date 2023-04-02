package org.snturk.petition.signature;

import org.snturk.petition.model.Issuer;

/**
 * Context that holds the signature information and issuer that applies signature of a petition.
 * @param issuer
 * @param signatureInfo
 */
public record SignatureContext(Issuer issuer, SignatureInfo signatureInfo) {}
