package com.cryfirock.auth.helper;

import java.util.function.Predicate;

public class ValidationHelper {
    private ValidationHelper() {
    }

    public static boolean isValidString(String value, Predicate<String> exists) {
        return !isBlankOrNull(value) && exists != null && !exists.test(value.trim());
    }

    public static boolean isBlankOrNull(String value) {
        return value == null || value.isBlank();
    }
}
