package org.snturk.petition;

import org.snturk.petition.enums.FileType;

import java.nio.file.Path;

/**
 * Generates files from Petition models
 */
public interface FileGenerationService {

    /**
     * Type of the generated file by this service implementation
     * @return Generated file type
     */
    FileType getFileType();

    /**
     * Generates a file from the given petition model and saves it to the given path
     * @param model Petition model
     * @param path Path to save the generated file
     */
    void generateFile(Petition model, Path path);


}
