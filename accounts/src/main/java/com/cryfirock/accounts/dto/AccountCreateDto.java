package com.cryfirock.accounts.dto;

import com.cryfirock.accounts.model.AccountAsset;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountOperational;
import com.cryfirock.accounts.model.AccountProductType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 1. DTO para la creación de una nueva cuenta.
 * 2. Contiene los datos mínimos requeridos para crear una cuenta.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 *
 * @param ownerId Identificador del propietario de la cuenta.
 * @param currency Código de moneda ISO 4217.
 * @param assetClass Clase de activo de la cuenta.
 * @param nature Naturaleza o finalidad de la cuenta.
 * @param operationalPurpose Propósito operativo del saldo.
 * @param productType Tipo de producto financiero.
 */
public record AccountCreateDto(
        @NotNull(message = "El identificador del propietario es obligatorio") Long ownerId,

        @NotBlank(message = "El código de moneda es obligatorio") @Size(min = 3, max = 3, message = "El código de moneda debe tener 3 caracteres") String currency,

        @NotNull(message = "La clase de activo es obligatoria") AccountAsset assetClass,

        @NotNull(message = "La naturaleza de la cuenta es obligatoria") AccountNature nature,

        @NotNull(message = "El propósito operativo es obligatorio") AccountOperational operationalPurpose,

        @NotNull(message = "El tipo de producto es obligatorio") AccountProductType productType) {
}
