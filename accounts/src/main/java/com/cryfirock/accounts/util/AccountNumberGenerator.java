package com.cryfirock.accounts.util;

import java.security.SecureRandom;

/**
 * 1. Utilidad para generar números de cuenta únicos.
 * 2. Genera números alfanuméricos de 16 caracteres.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public final class AccountNumberGenerator {
    // Caracteres permitidos para el número de cuenta.
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Longitud del número de cuenta generado.
    private static final int ACCOUNT_NUMBER_LENGTH = 16;

    // Generador de números aleatorios seguros.
    private static final SecureRandom RANDOM = new SecureRandom();

    // Constructor privado para evitar instanciación.
    private AccountNumberGenerator() {
        throw new UnsupportedOperationException("Clase de utilidad no instanciable");
    }

    /**
     * 1. Genera un número de cuenta único alfanumérico.
     * 2. Utiliza SecureRandom para mayor seguridad.
     *
     * @return Número de cuenta generado de 16 caracteres.
     */
    public static String generate() {
        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            accountNumber.append(CHARACTERS.charAt(index));
        }
        return accountNumber.toString();
    }
}
