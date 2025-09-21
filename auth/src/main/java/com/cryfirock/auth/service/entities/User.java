package com.cryfirock.auth.service.entities;

import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * ===================================================================================================
 * Paso 2.1: Definición de la entidad User que representa la tabla users
 * ===================================================================================================
 */

// Clase mapeada a tabla users
// Hibernate usa ese mapeo para convertir filas a objetos
@Entity
// Cuando clase y tabla no coinciden en nombre
// Permite añadir restricciones únicas a campos
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    // Clave primaria
    @Id
    // Generación automática incremental
    @GeneratedValue(strategy = GenerationType.UUID)
    // JPA hace el cast si los tipos no coinciden
    // El id solo se lee con getter y un int cabe en un long
    private Long id;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "given_name", nullable = false)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotEmpty(message = "{NotEmpty.user.givenName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.givenName}")
    @Size(min = 1, max = 50, message = "{Size.user.givenName}")
    private String givenName;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "family_name", nullable = false)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotEmpty(message = "{NotEmpty.user.familyName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.familyName}")
    @Size(min = 1, max = 50, message = "{Size.user.familyName}")
    private String familyName;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(nullable = false, unique = true)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @Email(message = "{Email.user.email}")
    @NotBlank(message = "{NotBlank.user.email}")
    @Size(min = 1, max = 100, message = "{Size.user.email}")
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotEmpty(message = "{NotEmpty.user.phoneNumber}")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "{Pattern.user.phoneNumber}")
    @Size(min = 9, max = 20, message = "{Size.user.phoneNumber}")
    private String phoneNumber;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(nullable = false)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotBlank(message = "{NotBlank.user.username}")
    @Size(min = 1, max = 50, message = "{Size.user.username}")
    private String username;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "password_hash", nullable = false)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotBlank(message = "{NotBlank.user.password}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;

    @Column(nullable = false)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotNull(message = "{NotNull.user.dob}")
    private LocalDate dob;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "account_status", nullable = false)
    private boolean active = true;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "updated_at")
    private Instant updatedAt;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "last_login_at")
    private Instant lastLoginAt;

}
