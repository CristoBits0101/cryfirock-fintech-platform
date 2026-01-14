package com.cryfirock.auth.validation.contract;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cryfirock.auth.validation.application.ExistsByUsernameValidationImpl;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * 1. Anotación personalizada para validar existencia de nombre.
 * 2. Objetivo de la validación: campos (FIELD).
 * 3. Retención en tiempo de ejecución (RUNTIME).
 * 4. ExistsByUsernameValidationImpl clase con la lógica de validación.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Constraint(validatedBy = ExistsByUsernameValidationImpl.class) @Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME)
public @interface IExistsByUsername {
    /**
     * 1. Define mensajes del mensaje de error.
     * 2. Grupos de validación es cuando se aplica la anotación (CRUD).
     * 3. Payload son metadatos de la anotación.
     */
    String message() default "Username already exists.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
