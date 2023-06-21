package org.snturk.petition.signature;

import org.snturk.petition.ImmutablePetitionModel;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.feedback.FeedbackModel;
import org.snturk.petition.utils.SignatureUtils;

import java.util.List;

/**
 * A service that handles the signature operations of a petition.
 */
public class SignatureService {

    /**
     * Performs the signature operation on the petition.
     * @param petitionModel PetitionModel
     * @param signers SignatureContext
     * @return PetitionModel
     */
    public PetitionModel performSign(PetitionModel petitionModel, SignatureContext ...signers) {

        SignatureUtils.validateSignature(petitionModel, signers);

        return ImmutablePetitionModel.builder()
                .from(petitionModel)
                .addSignatures(signers)
                .build();
    }

    /**
     * Performs signature operation on the feedback.
     * @param feedbackModel FeedbackModel
     * @param signers SignatureContext
     */
    public FeedbackModel performSign(FeedbackModel feedbackModel, SignatureContext ...signers) {

        SignatureUtils.validateSignature(feedbackModel, signers);

        return FeedbackModel.builder()
                .from(feedbackModel)
                .build().withSignatureContext(List.of(signers));
    }
}
