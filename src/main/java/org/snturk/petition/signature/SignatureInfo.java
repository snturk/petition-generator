package org.snturk.petition.signature;

import org.immutables.value.Value;

import java.time.LocalDateTime;

/**
 * A model that holds the signature information of a petition.
 */
@Value.Immutable
public interface SignatureInfo {

    static ImmutableSignatureInfo.Builder builder() {
        return ImmutableSignatureInfo.builder();
    }

    /**
     * Type of the applied signature
     * @return
     */
   SignatureType getSignatureType();

    /**
     * Signed date information of the signature
     * @return
     */
   LocalDateTime getSignedAt();
}
