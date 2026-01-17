package com.cryfirock.accounts.entity;

import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountStatus;

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
 * 1. Clase que representa una cuenta en el sistema.
 * 2. Mapea la entidad de cuenta a la tabla accounts en la base de datos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-17
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
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
    private AccountNature nature;
    // Estado actual de la cuenta.
    private AccountStatus status;
    // Atributos de tiempo para auditoría.
    @Embedded
    private Audit audit;
}
