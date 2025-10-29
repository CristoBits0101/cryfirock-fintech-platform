package com.cryfirock.auth.helper;

import java.util.function.Predicate;

public class ValidationHelper {
    private ValidationHelper() {
    }

    public static boolean isValidString(String value, Predicate<String> existenceChecker) {
        return existenceChecker == null
                || value == null
                || value.trim().isEmpty()
                || !existenceChecker.test(value.trim());
    }
}
