package org.snturk.petition.utils.config;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Common configuration class for getting properties from the properties file
 */
public class CommonConfiguration {

    public static final Logger LOGGER = Logger.getLogger(CommonConfiguration.class.getName());

    private CommonConfiguration() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the property value from the properties file
     * @param key Key of the property
     * @param defaultValue Default value of the property
     * @return Value of the property
     */
    public static String getProperty(String key, String defaultValue) {
        try {
            Properties properties = PropertiesLoader.loadProperties("application.properties");
            if (properties.containsKey(key)) {
                return properties.getProperty(key);
            }
            LOGGER.warning(String.format("Property %s is not found in the properties file. Default value will be used.", key));
            return defaultValue;
        } catch (IOException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
