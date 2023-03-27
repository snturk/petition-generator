package org.snturk.petition.utils.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for loading properties from the properties file
 */
public class PropertiesLoader {

    /**
     * Load the properties from the given resource file
     * @param resourceFileName Name of the resource file
     * @return Properties object
     * @throws IOException If the resource file is not found
     */
    public static Properties loadProperties(String resourceFileName) throws IOException {
        Properties configuration = new Properties();
        InputStream inputStream = PropertiesLoader.class
                .getClassLoader()
                .getResourceAsStream(resourceFileName);
        configuration.load(inputStream);
        inputStream.close();
        return configuration;
    }
}
