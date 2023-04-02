package org.snturk.petition.model;

import org.immutables.value.Value;
import org.snturk.petition.signature.SignatureContext;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Feedback definiton to hold petition feedback information.
 */
@Value.Immutable
public interface Feedback {

    static ImmutableFeedback.Builder builder() {
        return ImmutableFeedback.builder();
    }

    /**
     * Issuer of the Feedback
     */
    Issuer getIssuer();

    /**
     * Feedback message
     */
    String getContent();

    /**
     * Feedback date
     */
    @Value.Default
    default LocalDateTime getCreatedAt() {
        return LocalDateTime.now();
    }

    /**
     * Related attachments of the feedback
     */
    Set<File> getAttachments();

    /**
     * Signature context of the feedback
     */
    Set<SignatureContext> getSignatureContext();
}
