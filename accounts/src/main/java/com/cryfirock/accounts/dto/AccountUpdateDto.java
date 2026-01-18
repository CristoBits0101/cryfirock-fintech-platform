package com.cryfirock.accounts.dto;

import com.cryfirock.accounts.model.AccountStatus;

/**
 * 1. DTO para la actualización de una cuenta existente.
 * 2. Permite actualizar solo el estado de la cuenta.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 *
 * @param status Nuevo estado de la cuenta.
 */
public record AccountUpdateDto(AccountStatus status) {
}
