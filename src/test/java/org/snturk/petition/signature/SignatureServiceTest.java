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

public class SignatureServiceTest {

    private final SignatureService signatureService = new SignatureService();

    @Test @DisplayName("Perform Sign Test with Single Signer")
    void shouldPerformSignWithSingleSigner() throws IOException {
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContent.txt"));
        var issueDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addIssuers(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();
        var signedPetition = signatureService.performSign(petitionModelTemp, issuer);
        assertEquals(1, signedPetition.getSignatures().size());
        assertEquals(1, signedPetition.getSigners().length, signedPetition.getSignatures().get(0).getIssuers().length);
    }

    @Test @DisplayName("Perform Sign Test with Multiple Signers")
    void shouldPerformSignWithMultipleSigners() throws IOException {
        var issuer1 = new Person("Muratcan", "Senturk");
        var issuer2 = new Person("John", "Doe");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContent.txt"));
        var issueDate = LocalDateTime.of(2020, 1, 1, 12, 0, 0);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addIssuers(issuer1)
                .addIssuers(issuer2)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();
        var signedPetition = signatureService.performSign(petitionModelTemp, issuer1);
        assertEquals(1, signedPetition.getSignatures().size());
        assertEquals(2, signedPetition.getSigners().length, signedPetition.getSignatures().get(0).getIssuers().length);
    }

    @Test @DisplayName("Perform Sign Test with Invalid Date")
    void shouldPerformSignWithInvalidDate() throws IOException {
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContent.txt"));
        var issueDate = LocalDateTime.now().plusDays(1);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addIssuers(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();
        assertThrows(InvalidSignatureInfoException.class, () -> signatureService.performSign(petitionModelTemp, issuer));
    }

    @Test @DisplayName("Perform Sign Test with Invalid Signer")
    void shouldPerformSignWithInvalidSigner() throws IOException {
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContent.txt"));
        var issueDate = LocalDateTime.now().plusDays(1);
        var petitionModelTemp = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .addIssuers(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();
        assertThrows(InvalidSignatureInfoException.class, () -> signatureService.performSign(petitionModelTemp, new Person("John", "Doe")));
    }

    private String generateId(PetitionModel model) {
        return "TEST_ID";
    }
}
