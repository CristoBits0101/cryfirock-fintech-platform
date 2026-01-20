package com.cryfirock.accounts.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.cryfirock.accounts.model.AccountAsset;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountOperational;
import com.cryfirock.accounts.model.AccountProduct;
import com.cryfirock.accounts.model.AccountStatus;

/**
 * 1. DTO de respuesta con los datos de una cuenta.
 * 2. Contiene todos los campos relevantes para mostrar al cliente.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 *
 * @param id Identificador único de la cuenta.
 * @param accountNumber Número de cuenta único.
 * @param ownerId Identificador del propietario.
 * @param currency Código de moneda ISO 4217.
 * @param balance Saldo disponible de la cuenta.
 * @param assetClass Clase de activo de la cuenta.
 * @param nature Naturaleza o finalidad de la cuenta.
 * @param operationalPurpose Propósito operativo del saldo.
 * @param productType Tipo de producto financiero.
 * @param status Estado actual de la cuenta.
 * @param createdAt Fecha de creación de la cuenta.
 * @param updatedAt Fecha de última actualización.
 */
public record AccountResponseDto(
        Long id,
        String accountNumber,
        Long ownerId,
        String currency,
        BigDecimal balance,
        AccountAsset assetClass,
        AccountNature nature,
        AccountOperational operationalPurpose,
        AccountProduct productType,
        AccountStatus status,
        Instant createdAt,
        Instant updatedAt) {
}
