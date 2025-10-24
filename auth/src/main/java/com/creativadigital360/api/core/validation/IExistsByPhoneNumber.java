package com.creativadigital360.api.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * ============================================================================
 * Paso 6.1: Definir la anotación personalizada que usaremos en la entidad
 * ============================================================================
 */
// Vincula esta anotación personalizada con el validador que la implementa
// Cuando uses @IExistsByPhoneNumber se ejecutará ExistsByPhoneNumberValidationImpl
@Constraint(validatedBy = ExistsByPhoneNumberValidationImpl.class)
// Define sobre qué elementos del código se puede aplicar la anotación
// En este caso solo sobre campos de una clase
@Target(ElementType.FIELD)
// Indica en qué momento la anotación está disponible
// En tiempo de ejecución para que el validador pueda leerla por reflexión
@Retention(RetentionPolicy.RUNTIME)
// Declara una anotación personalizada mediante interface especial de anotación
public @interface IExistsByPhoneNumber {

    /**
     * ========================================================================
     * Paso 6.2: Definir elementos estándar de Bean Validation
     * ========================================================================
     */
    // Mensaje de error que se mostrará si la validación falla
    String message() default "Phone number already exists.";

    // Grupos usados para organizar validaciones
    Class<?>[] groups() default {};

    // Carga útil para adjuntar metadatos a la validación
    Class<? extends Payload>[] payload() default {};
}
