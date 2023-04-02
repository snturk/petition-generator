package org.snturk.petition.utils;

import org.snturk.petition.PetitionModel;
import org.snturk.petition.exceptions.InvalidSignatureInfoException;
import org.snturk.petition.model.Feedback;
import org.snturk.petition.model.Issuer;
import org.snturk.petition.signature.SignatureContext;

import java.util.List;
import java.util.Set;

/**
 * Utility class for signature operations.
 */
public class SignatureUtils {

    private SignatureUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Validates for several conditions in case that the petition is signed.
     * @param targetPetition PetitionModel
     * @param context SignatureContext
     */
    public static void validateSignature(PetitionModel targetPetition, SignatureContext ...context) {

        for (SignatureContext signatureContext : context) {
            Issuer signer = signatureContext.issuer();

            // A petition cannot signed by any Issuer other than the petitioners
            List<Issuer> petitioners = targetPetition.getPetitioners();
            if (!petitioners.contains(signer)) {
                throw new InvalidSignatureInfoException("A petition cannot signed by any Issuer other than the petitioners");
            }

            // A petition cannot signed by same issuer other that in feedbacks
            Set<SignatureContext> signatures = targetPetition.getSignatures();
            if (signatures != null) {
                for (SignatureContext signature : signatures) {
                    if (signature.issuer().equals(signer)) {
                        throw new InvalidSignatureInfoException("A petition cannot signed by same issuer other that in feedbacks");
                    }
                }
            }


            // A petition cannot be signed by a context that generated before the petition
            if (!targetPetition.getIssuedDate().isBefore(signatureContext.signatureInfo().getSignedAt())) {
                throw new InvalidSignatureInfoException("A petition cannot be signed by a context that generated before the petition");
            }
        }

    }

    /**
     * Validates for several conditions in case that the feedback is signed.
     * @param targetFeedback Feedback
    * @param context SignatureContext
    */
    public static void validateSignature(Feedback targetFeedback, SignatureContext context) {

        Issuer signer = context.issuer();

        // A feedback cannot signed by any Issuer other than feedbackers
        Issuer feedbacker = targetFeedback.getIssuer();
        if (!feedbacker.equals(signer)) {
            throw new InvalidSignatureInfoException("A feedback cannot signed by any Issuer other than feedbackers");
        }
    }
}
