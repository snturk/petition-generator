package org.snturk.petition.signature;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.ImmutablePetitionModel;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.PetitionType;
import org.snturk.petition.exceptions.InvalidSignatureInfoException;
import org.snturk.petition.model.Person;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignatureServiceTest {

    private final SignatureService signatureService = new SignatureService();

    @Test @DisplayName("Perform Sign Test with Single Signer")
    void shouldPerformSignWithSingleSigner() throws IOException {
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContentHtml.txt"));
        var issueDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addPetitioners(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();

        var signatureInfo = ImmutableSignatureInfo.builder()
                .signatureType(new TextBasedSignature())
                .signedAt(LocalDateTime.now())
                .build();
        var signatureContext = new SignatureContext(issuer, signatureInfo);
        var signedPetition = signatureService.performSign(petitionModelTemp, signatureContext);
        assertEquals(1, signedPetition.getSignatures().size());
        assertEquals(1, signedPetition.getPetitioners().size(), signedPetition.getSignatures().size());
    }

    @Test @DisplayName("Perform Sign Test with Multiple Signers")
    void shouldPerformSignWithMultipleSigners() throws IOException {
        var issuer1 = new Person("Muratcan", "Senturk");
        var issuer2 = new Person("John", "Doe");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContentHtml.txt"));
        var issueDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addPetitioners(issuer1)
                .addPetitioners(issuer2)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();

        var signatureInfo = ImmutableSignatureInfo.builder()
                .signatureType(new TextBasedSignature())
                .signedAt(LocalDateTime.now())
                .build();
        var signatureContext = new SignatureContext(issuer1, signatureInfo);
        var signedPetition = signatureService.performSign(petitionModelTemp, signatureContext);
        assertEquals(1, signedPetition.getSignatures().size());
        assertEquals(1, signedPetition.getPetitioners().size(), signedPetition.getSignatures().size());
    }

    @Test @DisplayName("Perform Sign Test with Invalid Date")
    void shouldPerformSignWithInvalidDate() throws IOException {
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContentHtml.txt"));
        var issueDate = LocalDateTime.now();
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addPetitioners(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();

        var signatureInfo = ImmutableSignatureInfo.builder()
                .signatureType(new TextBasedSignature())
                .signedAt(LocalDateTime.now().minusDays(2))
                .build();
        var signatureContext = new SignatureContext(issuer, signatureInfo);
        assertThrows(InvalidSignatureInfoException.class, () -> signatureService.performSign(petitionModelTemp, signatureContext));
    }

    @Test @DisplayName("Perform Sign Test with Invalid Signer")
    void shouldPerformSignWithInvalidSigner() throws IOException {
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContentHtml.txt"));
        var issueDate = LocalDateTime.now().plusDays(1);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addPetitioners(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();

        var signatureInfo = ImmutableSignatureInfo.builder()
                .signatureType(new TextBasedSignature())
                .signedAt(LocalDateTime.now())
                .build();
        var wrongSignatureContext = new SignatureContext(new Person("John", "Doe"), signatureInfo);
        assertThrows(InvalidSignatureInfoException.class, () -> signatureService.performSign(petitionModelTemp, wrongSignatureContext));
    }

    private String generateId(PetitionModel model) {
        return "TEST_ID";
    }
}
