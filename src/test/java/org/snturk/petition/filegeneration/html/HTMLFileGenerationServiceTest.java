package org.snturk.petition.filegeneration.html;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.snturk.petition.ImmutablePetitionModel;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.PetitionType;
import org.snturk.petition.model.Person;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HTMLFileGenerationServiceTest {

    private HTMLFileGenerationService htmlFileGenerationService = new HTMLFileGenerationService();

    @Test @DisplayName("HTML File Generation Service Test")
    void shouldGenerateHTMLFile() throws IOException {
        // First we need to create a petition model
        var issuer = new Person("Muratcan", "Senturk");
        var testContent = FileUtils.readFileToString(new File("src/test/resources/testContent.txt"));
        var issueDate = LocalDate.of(2020, 1, 1);
        var petitionModel = ImmutablePetitionModel.builder()
                .idGenerator(this::generateId)
                .name("Test Petition")
                .title("Test Petition Title")
                .issuer(issuer)
                .content(testContent)
                .type(PetitionType.REQUEST)
                .issuedDate(issueDate)
                .build();

        // Then we can generate the file
        var path = new File("src/test/resources/generated.html").toPath();
        htmlFileGenerationService.generateFile(petitionModel, path);

        // Now we can check the generated file
        var generatedFile = FileUtils.readFileToString(new File("src/test/resources/generated.html"));
        var expectedFile = FileUtils.readFileToString(new File("src/test/resources/test1ExpectedResult.html"));
        // Compare without indentation
        assertEquals(expectedFile.replaceAll("\\s+", ""), generatedFile.replaceAll("\\s+", ""));
    }

    private String generateId(PetitionModel model) {
        return "3d3d3d3d-3d3d-3d3d-3d3d-3d3d3d3d3d3d";
    }
}
