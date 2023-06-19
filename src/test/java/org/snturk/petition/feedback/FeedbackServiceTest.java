package org.snturk.petition.feedback;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.PetitionType;
import org.snturk.petition.model.Person;
import org.snturk.petition.signature.ImmutableSignatureInfo;
import org.snturk.petition.signature.SignatureContext;
import org.snturk.petition.signature.SignatureInfo;
import org.snturk.petition.signature.SignatureService;
import org.snturk.petition.signature.TextBasedSignature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test @DisplayName("Add Single Feedback to Petition")
    void shouldAddSingleFeedbackToPetition() throws IOException {
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

        SignatureContext finalSignatureContext = signatureContext;
        Assertions.assertDoesNotThrow(() -> signatureService.performSign(feedback, finalSignatureContext));

        Person issuer = new Person("Muratcan", "Senturk");
        String testContent = FileUtils.readFileToString(new File("src/test/resources/testContentHtml.txt"));
        LocalDateTime issueDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        PetitionModel petitionModelTemp = PetitionModel.builder()
                .idGenerator((petitionModel) -> "test_petition")
                .name("Test Petition")
                .title("Test Petition Title")
                .addPetitioners(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();

        signatureInfo = ImmutableSignatureInfo.builder()
                .signatureType(new TextBasedSignature())
                .signedAt(LocalDateTime.now())
                .build();
        signatureContext = new SignatureContext(issuer, signatureInfo);
        var signedPetition = signatureService.performSign(petitionModelTemp, signatureContext);

        var feedbackedPetition = feedbackService.issueFeedback(feedback, signedPetition);
        assertNotNull(feedbackedPetition.getFeedbacks());
        assertEquals(1, feedbackedPetition.getFeedbacks().size());
        assertEquals(feedbackedPetition.getFeedbacks().get(0).getPetitionId(), signedPetition.getId());
    }

}
