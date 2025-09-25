package com.cryfirock.auth.service.entities;

import java.time.LocalDate;
import java.util.List;

import com.cryfirock.auth.service.validations.IExistsByEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
// Genera constructor vacío
@NoArgsConstructor
// Genera constructor con todos los campos
@AllArgsConstructor
// Genera getters
@Getter
// Genera setters
@Setter
public class User {

    /**
     * ===============================================================================================
     * Paso 2.2: Columnas
     * ===============================================================================================
     */

    // Clave primaria
    @Id
    // Generación automática incremental
    @GeneratedValue(strategy = GenerationType.UUID)
    // JPA hace el cast si los tipos no coinciden
    // El id solo se lee con getter y un int cabe en un long
    // El id lo genera la base de datos
    // Si dejas un setId() alguien podría asignar un valor y romper la integridad
    @Setter(lombok.AccessLevel.NONE)
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
    @IExistsByEmail(message = "{Email.user.email}")
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
    @Column(nullable = false)
    // Valida el objeto en memoria antes de guardarlo en la base de datos
    @NotNull(message = "{NotNull.user.address}")
    private String address;

    // JPA mapea atributo a columna cuando no coinciden
    // Restricciones aplicadas al generar la tabla desde JPA
    @Column(name = "account_status", nullable = false)
    private boolean enabled = true;

    // Cuando convierto a JSON no meto el campo users del otro lado
    // Role mantiene una lista de users y User mantiene una lista de roles
    // El bucle ocurre por las referencias en memoria entre instancias
    // Es una referencia cíclica en el grafo de objetos en memoria
    // Handler y hibernateLazyInitializer los añade Hibernate al usar lazy loading
    // También se ignoran para no exponerlos en el JSON
    @JsonIgnoreProperties({ "users", "handler", "hibernateLazyInitializer" })
    @JoinTable(
            // Tabla que almacena claves externas relacionales
            name = "users_roles",
            // Clave externa que pertenece a la propia entidad
            joinColumns = @JoinColumn(name = "user_id"),
            // Clave externa perteneciente a la entidad opuesta
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            // Establishes that relationships are unique
            uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "role_id" }))
    @ManyToMany
    private List<Role> roles;

    // No se persiste en la BD y no es una columna de la tabla
    @Transient
    // Se acepta en la deserialización y se omite al serializar
    // Permite el valor en la petición y lo oculta en la respuesta del JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    // Se ejecuta antes de almacenar un usuario
    @PrePersist
    public void prePersist() {
        // El usuario está habilitado la primera vez que se almacena
        enabled = true;
    }

}
