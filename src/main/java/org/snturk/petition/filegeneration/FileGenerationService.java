package org.snturk.petition.filegeneration;

import org.snturk.petition.PetitionModel;
import org.snturk.petition.enums.FileType;
import org.snturk.petition.utils.config.CommonConfiguration;

import java.nio.file.Path;

/**
 * Generates files from PetitionModel models
 */
public interface FileGenerationService {

    /**
     * Prefix for the properties of this service implementation
     */
    String PREFIX = "petition-generator.file-generation.";
    /**
     * Default template path for the file generation
     */
    String DEFAULT_TEMPLATE_PATH = "src/main/resources/templates/%s/petitionTemplate.%s";

    /**
     * Type of the generated file by this service implementation
     * @return Generated file type
     */
    FileType getFileType();

    /**
     * Generates a file from the given petition model and saves it to the given path
     * @param model PetitionModel model
     * @param path Path to save the generated file
     */
    void generateFile(PetitionModel model, Path path);

    /**
     * Get the computed path of the template file, if it is not set in the properties, default path will be used
     * @return Path of the template file
     */
    default Path getTemplatePath() {
        String format = getFileType().name().toLowerCase();
        String templatePath = String.format(DEFAULT_TEMPLATE_PATH, format, format);
        String templatePathFull = CommonConfiguration.getProperty(PREFIX + format + ".template-path", templatePath);

        return Path.of(templatePathFull);
    }


}
