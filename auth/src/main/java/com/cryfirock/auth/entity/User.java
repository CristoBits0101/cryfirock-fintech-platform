package com.cryfirock.auth.entity;

import java.time.LocalDate;
import java.util.List;

import com.cryfirock.auth.model.AccountStatus;
import com.cryfirock.auth.validation.contract.IExistsByEmail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * 1. Entidad JPA que representa una tabla en la base de datos.
 * 2. Nombre de la tabla en la base de datos.
 * 3. Define una restricción de unicidad en la columna "username".
 * 4. Genera un constructor sin argumentos.
 * 5. Genera un constructor con todos los argumentos.
 * 6. Genera getters y setters para todos los campos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    /**
     * 1. Clave primaria de la entidad.
     * 2. Generación automática del valor de la clave primaria.
     * 3. Columna de la tabla que almacena el ID del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

    /**
     * 1. Columna de la tabla que almacena nombre(s) no nulos del usuario.
     * 2. El nombre no puede estar vacío.
     * 3. Solo se permiten letras mayúsculas y minúsculas o caracteres acentuados.
     * 4. Tamaño mínimo de 1 carácter y máximo de 50 caracteres.
     * 5. Nombre(s) del usuario.
     */
    @Column(name = "given_name", nullable = false)
    @NotEmpty(message = "{NotEmpty.user.givenName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+$", message = "{Pattern.user.givenName}")
    @Size(min = 1, max = 50, message = "{Size.user.givenName}")
      private String givenName;

    /**
     * 1. Columna de la tabla que almacena apellidos no nulos del usuario.
     * 2. El apellido no puede estar vacío.
     * 3. Permite letras mayúsculas y minúsculas, caracteres acentuados y espacios.
     * 4. Tamaño mínimo de 1 carácter y máximo de 50 caracteres.
     * 5. Apellidos del usuario.
     */
    @Column(name = "family_name", nullable = false)
    @NotEmpty(message = "{NotEmpty.user.familyName}")
    @Pattern(regexp = "^[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+(\\s[A-Za-zÁáÉéÍíÓóÚúÝýÆæØøÅåÄäÖöÑñÜüß]+)*$", message = "{Pattern.user.familyName}")
    @Size(min = 1, max = 50, message = "{Size.user.familyName}")
      private String familyName;

    /**
     * 1. Columna de la tabla que almacena un email irrepetible.
     * 2. Valida que el email no exista en la base de datos.
     * 3. El email no puede ser nulo, vacío o con espacios.
     * 4. Tamaño mínimo de 1 carácter y máximo de 100 caracteres.
     * 5. Email del usuario.
     */
    @Column(nullable = false, unique = true)
    @IExistsByEmail(message = "{ExistsByEmail.user.email}")
    @NotBlank(message = "{NotBlank.user.email}")
    @Size(min = 1, max = 100, message = "{Size.user.email}")
      private String email;

    /**
     * 1. Columna de la tabla que almacena un número de teléfono irrepetible.
     * 2. El número de teléfono no puede ser nulo o vacío.
     * 3. Solo se permiten dígitos numéricos.
     * 4. Tamaño mínimo de 9 dígitos y máximo de 20 dígitos.
     * 5. Número de teléfono del usuario.
     */
    @Column(name = "phone_number", nullable = false, unique = true)
    @NotEmpty(message = "{NotEmpty.user.phoneNumber}")
    @Pattern(regexp = "^[0-9]{9,20}$", message = "{Pattern.user.phoneNumber}")
    @Size(min = 9, max = 20, message = "{Size.user.phoneNumber}")
      private String phoneNumber;

    /**
     * 1. Columna de la tabla que almacena un nombre de usuario irrepetible.
     * 2. El nombre de usuario no puede ser nulo o vacío.
     * 3. Tamaño mínimo de 1 carácter y máximo de 50 caracteres.
     * 4. Nombre de usuario del usuario.
     */
    @Column(nullable = false)
    @NotBlank(message = "{NotBlank.user.username}")
    @Size(min = 1, max = 50, message = "{Size.user.username}")
      private String username;

    /**
     * 1. Columna de la tabla que almacena el hash de la contraseña.
     * 2. El hash de la contraseña no puede ser nulo o vacío.
     * 3. El hash de la contraseña solo se puede escribir y no se lee desde JSON.
     * 4. Hash de la contraseña del usuario.
     */
    @Column(name = "password_hash", nullable = false)
    @NotBlank(message = "{NotBlank.user.password}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private String passwordHash;

    /**
     * 1. Columna de la tabla que almacena la fecha de nacimiento.
     * 2. La fecha de nacimiento no puede ser nula.
     * 3. Fecha de nacimiento del usuario.
     */
    @Column(nullable = false)
    @NotNull(message = "{NotNull.user.dob}")
      private LocalDate dob;

    /**
     * 1. Columna de la tabla que almacena la dirección.
     * 2. La dirección no puede ser nula.
     * 3. Dirección del usuario.
     */
    @Column(nullable = false)
    @NotNull(message = "{NotNull.user.address}")
      private String address;

    /**
     * 1. Columna de la tabla que almacena el estado de la cuenta.
     * 2. El estado de la cuenta no puede ser nulo.
     * 3. Estado de la cuenta del usuario.
     */
    @Column(name = "account_status", nullable = false)
    @Enumerated(EnumType.STRING)
      private AccountStatus enabled = AccountStatus.ACTIVE;

    /**
     * 1. Evita referencias circulares al serializar a JSON.
     * 2. Lista de roles asociados a este usuario.
     * 3. Relación muchos a muchos con la entidad Role.
     */
    @JsonIgnoreProperties({ "users", "handler", "hibernateLazyInitializer" })
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
        "user_id", "role_id" }))
    @ManyToMany
      private List<Role> roles;

    /**
     * 1. No se mapea a ninguna columna de la tabla.
     * 2. Solo se puede escribir y no se lee desde JSON.
     * 3. Indica si el usuario tiene rol de administrador.
     */
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
      private boolean admin;

    /**
     * 1. Método que se ejecuta antes de persistir la entidad.
     * 2. Asigna el estado ACTIVE al campo enabled por defecto.
     */
    @PrePersist
    public void prePersist() {
        enabled = AccountStatus.ACTIVE;
    }

    /**
     * 1. No se mapea a ninguna columna de la tabla.
     * 2. Puerto del servicio de autenticación.
     */
    @Transient
    private int port;
}
