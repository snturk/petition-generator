package org.snturk.petition.filegeneration.html;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.ImmutablePetitionModel;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.PetitionType;
import org.snturk.petition.model.Person;
import org.snturk.petition.signature.ImmutableSignatureInfo;
import org.snturk.petition.signature.SignatureContext;
import org.snturk.petition.signature.SignatureService;
import org.snturk.petition.signature.TextBasedSignature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlFileGenerationServiceTest {

    private HtmlFileGenerationService htmlFileGenerationService = new HtmlFileGenerationService();
    private SignatureService signatureService = new SignatureService();

    @Test @DisplayName("HTML File Generation Service Test")
    void shouldGenerateHTMLFile() throws IOException {
        // First we need to create a petition model
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
        var petitionModel = signatureService.performSign(petitionModelTemp, signatureContext);

        // Then we can generate the file
        var path = new File("src/test/resources/generated.html").toPath();
        htmlFileGenerationService.generateFile(petitionModel, path);

        // Now we can check the generated file
        var generatedFile = FileUtils.readFileToString(new File("src/test/resources/generated.html"));
        var expectedFile = FileUtils.readFileToString(new File("src/test/resources/test1ExpectedResult.html"));
        // Compare without indentation and time differences
        expectedFile = expectedFile.replace("{{signedAt}}", signatureInfo.getSignedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        assertEquals(expectedFile.replaceAll("\\s+", ""), generatedFile.replaceAll("\\s+", ""));
    }

    @Test @DisplayName("HTML File Generation Service Test with multiple signers")
    void shouldGenerateHTMLFileWithMultipleSigners() throws IOException {
        // First we need to create a petition model
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
        var signatureContext1 = new SignatureContext(issuer1, signatureInfo);
        var signatureContext2 = new SignatureContext(issuer2, signatureInfo);
        var petitionModel = signatureService.performSign(petitionModelTemp, signatureContext1);
        petitionModel = signatureService.performSign(petitionModel, signatureContext2);

        // Then we can generate the file
        var path = new File("src/test/resources/generated.html").toPath();
        htmlFileGenerationService.generateFile(petitionModel, path);

        // Now we can check the generated file
        var generatedFile = FileUtils.readFileToString(new File("src/test/resources/generated.html"));
        var expectedFile = FileUtils.readFileToString(new File("src/test/resources/test2ExpectedResult.html"));
        // Compare without indentation
        expectedFile = expectedFile.replace("{{signedAt}}", signatureInfo.getSignedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        assertEquals(expectedFile.replaceAll("\\s+", ""), generatedFile.replaceAll("\\s+", ""));
    }

    private String generateId(PetitionModel model) {
        return "TEST_ID";
    }
}
