package org.snturk.petition.utils;

import jakarta.annotation.Nullable;

/**
 * Utility class for string operations
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("StringUtils is a utility class and cannot be instantiated");
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.length() == 0;
    }
}
