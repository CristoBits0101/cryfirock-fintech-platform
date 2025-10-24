package com.creativadigital360.api.core.util;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private final PasswordEncoder passwordEncoder;

    private static final Predicate<String> IS_BCRYPT = startsWithAny("$2a$", "$2b$", "$2y$");

    public PasswordUtils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static Predicate<String> startsWithAny(String... p) {
        return s -> s != null && Arrays.stream(p).anyMatch(s::startsWith);
    }

    public String encodeIfRaw(String rawOrHash) {
        if (rawOrHash == null)
            return null;
        return IS_BCRYPT.test(rawOrHash) ? rawOrHash : passwordEncoder.encode(rawOrHash);
    }

}
