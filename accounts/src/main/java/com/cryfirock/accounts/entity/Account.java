package com.cryfirock.accounts.entity;

import com.cryfirock.accounts.model.AccountStatus;
import com.cryfirock.accounts.model.AccountType;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 1. Entidad JPA que representa una tabla en la base de datos.
 * 2. Genera getters y setters para todos los campos.
 * 3. Genera un constructor sin argumentos.
 */
@Entity @Setter @Getter @NoArgsConstructor @AllArgsConstructor()
public class Account {
    // Clave primaria auto-generada indetificadora de la cuenta.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Identificador del propietario de la cuenta.
    private Long ownerId;
    // Moneda en la que se maneja la cuenta.
    private String currency;
    // Finalidad de la cuenta.
    private AccountType type;
    // Estado actual de la cuenta.
    private AccountStatus status;
    // Atributos de tiempo para auditoría.
    @Embedded
    private Audit audit;
}
