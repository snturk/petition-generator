package org.snturk.petition.feedback;

import org.snturk.petition.PetitionModel;
import org.snturk.petition.exceptions.InvalidFeedbackException;

/**
 * FeedbackService is a service class that holds all core features of a feedback.
 */
public class FeedbackService {

    /**
     * Issues a feedback for a petition and returns the petition with the feedback.
     * @param feedback Feedback to be issued
     * @param petition Petition that feedback will be added
     * @return Petition with the feedback
     */
    public PetitionModel issueFeedback(FeedbackModel feedback, PetitionModel petition) {
        validateFeedback(feedback, petition);

        // Add petitionId to feedback
        feedback = FeedbackModel.builder()
                .from(feedback)
                .petitionId(petition.getId())
                .build();

        return PetitionModel.builder()
                .from(petition)
                .addFeedbacks(feedback)
                .build();
    }

    /**
     * Validates a feedback for several conditions.
     * @param feedback Feedback to be validated
     * @param petition Petition that feedback belongs to
     */
    public void validateFeedback(FeedbackModel feedback, PetitionModel petition) {
        // A feedback cannot be created before the petition
        if (!petition.getIssuedDate().isBefore(feedback.getIssuedDate())) {
            throw new InvalidFeedbackException("A feedback cannot be signed by a context that generated before the feedback", feedback);
        }

        // A feedback cannot be signed by a context that generated before the petition
        if (feedback.getSignatureContext() != null) {
            feedback.getSignatureContext().forEach(signatureContext -> {
                if (!petition.getIssuedDate().isBefore(signatureContext.signatureInfo().getSignedAt())) {
                    throw new InvalidFeedbackException("A feedback cannot be signed by a context that generated before the petition", feedback);
                }
            });
        }

        // A feedback cannot be added to a petition that is already contains the feedback
        if (petition.getFeedbacks() != null && petition.getFeedbacks().contains(feedback)) {
            throw new InvalidFeedbackException("A feedback cannot be added to a petition that is already contains the feedback", feedback);
        }
    }
}
