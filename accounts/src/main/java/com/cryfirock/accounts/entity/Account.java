package com.cryfirock.accounts.entity;

import com.cryfirock.accounts.model.AccountStatus;
import com.cryfirock.accounts.model.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

// Clase entidad que representa una tabla de cuenta financiera en la base de datos.
@Entity @Getter @Setter
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

    /**
     * 1. Método que se ejecuta antes de persistir la entidad.
     * 2. Asigna el estado ACTIVE al campo enabled por defecto.
     */
    @PrePersist
    public void prePersist() {
        enabled = AccountStatus.ACTIVE;
    }
}
