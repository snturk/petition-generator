package org.snturk.petition.filegeneration.pdf;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
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
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PdfFileGenerationServiceTest {

    private final PdfFileGenerationService pdfFileGenerationService = new PdfFileGenerationService();
    private final SignatureService signatureService = new SignatureService();

    @BeforeAll
    static void setup() {
        // Clear all .pdf files in the test resources folder
        var testResourcesFolder = new File("src/test/resources");
        var files = testResourcesFolder.listFiles((dir, name) -> name.endsWith(".pdf"));
        if (files != null) {
            for (var file : files) {
                file.delete();
            }
        }
    }

    @Test @DisplayName("PDF File Generation Service Test")
    void shouldGeneratePDFFile() throws IOException {
        var issuer1 = new Person("Muratcan", "Senturk");
        var issuer2 = new Person("John", "Doe");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContentPdf.txt"));
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
        var petitionModel = signatureService.performSign(petitionModelTemp, signatureContext1);

        Path path = Path.of("src/test/resources/testGenerated.pdf");
        pdfFileGenerationService.generateFile(petitionModel, path);

        // Assertions

        assertTrue(path.toFile().exists());
        assertTrue(path.toFile().length() > 0);
    }

    private String generateId(PetitionModel model) {
        return "testId";
    }
}
