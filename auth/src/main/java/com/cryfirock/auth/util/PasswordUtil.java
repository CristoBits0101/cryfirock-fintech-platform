package com.cryfirock.auth.util;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordUtil {
  private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
  private static final Predicate<String> IS_BCRYPT = startsWithAny("$2a$", "$2b$", "$2y$");

  private PasswordUtil() {
  }

  private static Predicate<String> startsWithAny(String... p) {
    return s -> s != null && Arrays.stream(p).anyMatch(s::startsWith);
  }

  public static String encodeIfRaw(String rawOrHash) {
    if (rawOrHash == null) return null;
    return IS_BCRYPT.test(rawOrHash)
        ? rawOrHash
        : ENCODER.encode(rawOrHash);
  }
}
