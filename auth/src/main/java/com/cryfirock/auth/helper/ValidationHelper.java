package com.cryfirock.auth.helper;

import java.util.function.Predicate;

public class ValidationHelper {
    private ValidationHelper() {
    }

    public static boolean isValidString(String value, Predicate<String> exists) {
        return exists != null && !isBlankOrNull(value) && !exists.test(value.trim());
    }

    public static boolean isBlankOrNull(String value) {
        return value == null || value.isBlank();
    }
}
