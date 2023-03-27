package org.snturk.petition.filegeneration;

import org.snturk.petition.enums.FileType;
import org.snturk.petition.utils.config.CommonConfiguration;

import java.nio.file.Path;

/**
 * Configuration class for file generation
 * Environment variables can be used to configure this class
 */
public class FileGenerationConfig {

    private static final String PREFIX = "petition-generator.file-generation.";
    private static final String DEFAULT_TEMPLATE_PATH = "src/main/resources/templates/html/petitionTemplate.html";

    private final FileType fileType;

    public FileGenerationConfig(FileType fileType) {
        this.fileType = fileType;
    }

    /**
     * Get the file type of the generated file
     * @return File type
     */
    public FileType getFileType() {
        return fileType;
    }

    /**
     * Get the computed path of the template file, if it is not set in the properties, default path will be used
     * @return Path of the template file
     */
    public Path getTemplatePath() {
        String templatePath = CommonConfiguration.getProperty(PREFIX + fileType.name().toLowerCase() + ".template-path", DEFAULT_TEMPLATE_PATH);

        return Path.of(templatePath);
    }
}
