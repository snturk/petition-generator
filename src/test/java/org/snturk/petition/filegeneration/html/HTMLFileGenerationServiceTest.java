package org.snturk.petition.filegeneration.html;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.ImmutablePetitionModel;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.PetitionType;
import org.snturk.petition.model.Person;
import org.snturk.petition.signature.SignatureService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTMLFileGenerationServiceTest {

    private HTMLFileGenerationService htmlFileGenerationService = new HTMLFileGenerationService();
    private SignatureService signatureService = new SignatureService();

    @Test @DisplayName("HTML File Generation Service Test")
    void shouldGenerateHTMLFile() throws IOException {
        // First we need to create a petition model
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

        var petitionModel = signatureService.performSign(petitionModelTemp, issuer);

        // Then we can generate the file
        var path = new File("src/test/resources/generated.html").toPath();
        htmlFileGenerationService.generateFile(petitionModel, path);

        // Now we can check the generated file
        var generatedFile = FileUtils.readFileToString(new File("src/test/resources/generated.html"));
        var expectedFile = FileUtils.readFileToString(new File("src/test/resources/test1ExpectedResult.html"));
        // Compare without indentation
        assertEquals(expectedFile.replaceAll("\\s+", ""), generatedFile.replaceAll("\\s+", ""));
    }

    @Test @DisplayName("HTML File Generation Service Test with multiple signers")
    void shouldGenerateHTMLFileWithMultipleSigners() throws IOException {
        // First we need to create a petition model
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

        var petitionModel = signatureService.performSign(petitionModelTemp, issuer1);
        petitionModel = signatureService.performSign(petitionModel, issuer2);

        // Then we can generate the file
        var path = new File("src/test/resources/generated.html").toPath();
        htmlFileGenerationService.generateFile(petitionModel, path);

        // Now we can check the generated file
        var generatedFile = FileUtils.readFileToString(new File("src/test/resources/generated.html"));
        var expectedFile = FileUtils.readFileToString(new File("src/test/resources/test2ExpectedResult.html"));
        // Compare without indentation
        assertEquals(expectedFile.replaceAll("\\s+", ""), generatedFile.replaceAll("\\s+", ""));
    }

    private String generateId(PetitionModel model) {
        return "TEST_ID";
    }
}
