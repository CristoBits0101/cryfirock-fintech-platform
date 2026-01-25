package com.cryfirock.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Entidad que relaciona cuentas con usuarios.
 * 2. Guarda el identificador del usuario del microservicio auth.
 * 3. Representa la relación muchos a muchos entre cuentas y usuarios.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
@Entity @Table(
        // Tabla que relaciona cuentas bancarias con usuarios.
        name = "account_user",
        // 1. Restricción única para evitar duplicados de cuenta y usuario.
        // 2. Ejemplo: Una cuenta bancaria no puede tener dos usuarios.
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "account_id", "user_id" })
        }) @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AccountUser {
    /**
     * Constructor personalizado.
     *
     * @param accountId Identificador de la cuenta.
     * @param userId Identificador del usuario.
     */
    public AccountUser(Long accountId, Long userId) {
        this.accountId = accountId;
        this.userId = userId;
    }

    // 1. En la base de datos no existe un id autogenerado.
    // 2. Todas las entidades deben tener un id autogenerado.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. Identificador de la cuenta asociada.
    // 2. Ejemplo: 1001
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    // 1. Identificador del usuario asociado.
    // 2. Ejemplo: 523
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 1. Metadatos de manipulación de la cuenta bancaria.
    // 2. Fecha en la que el usuario se relaciona con la cuenta o cambia de propietario.
    // 3. Ejemplo: 07/01/2025 10:15:30
    @Embedded
    private Audit audit;
}
