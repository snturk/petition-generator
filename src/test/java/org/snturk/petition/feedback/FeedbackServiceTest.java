package org.snturk.petition.feedback;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.model.Person;
import org.snturk.petition.signature.SignatureContext;
import org.snturk.petition.signature.SignatureInfo;
import org.snturk.petition.signature.SignatureService;
import org.snturk.petition.signature.TextBasedSignature;

class FeedbackServiceTest {

    private final FeedbackService feedbackService = new FeedbackService();
    private final SignatureService signatureService = new SignatureService();

    @Test @DisplayName("Perform Sign Test with Single Signer on Feedback")
    void shouldSignFeedbackWithSingleSigner() {
        Person signer = new Person("John", "Doe");
        FeedbackModel feedback = FeedbackModel.builder()
                .issuer(signer)
                .idGenerator((feedbackModel) -> "feedback_single_sign_test")
                .content("This is a test feedback")
                .build();

        // Signatures
        SignatureInfo signatureInfo = SignatureInfo.builder()
                .signatureType(new TextBasedSignature())
                .build();
        SignatureContext signatureContext = new SignatureContext(signer, signatureInfo);

        Assertions.assertDoesNotThrow(() -> signatureService.performSign(feedback, signatureContext));
    }
}
