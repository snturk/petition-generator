package org.snturk.petition.filegeneration.html;

import org.apache.commons.io.FileUtils;
import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.FileType;
import org.snturk.petition.filegeneration.FileGenerationService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class HTMLFileGenerationService implements FileGenerationService {

    @Override
    public FileType getFileType() {
        return FileType.HTML;
    }

    @Override
    public void generateFile(PetitionModel model, Path path) {
        File templateFile = new File("src/main/resources/templates/html/petitionTemplate.html");
        String html = prepareTemplate(templateFile, model);

        File htmlFile = path.toFile();

        try {
            FileUtils.writeStringToFile(htmlFile, html, StandardCharsets.UTF_8.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill the petitionTemplate.html with the given model
     * @param model PetitionModel model
     * @return Ready-to-use html string
     */
    private String prepareTemplate(File templateFile, PetitionModel model) {
        try {
            String template = FileUtils.readFileToString(templateFile);
            return HTMLTemplateFiller.fillTemplate(template, model);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
