package com.cryfirock.account.dto;

import java.math.BigDecimal;
import java.util.List;

import com.cryfirock.account.entity.Audit;
import com.cryfirock.account.type.AccountAssets;
import com.cryfirock.account.type.AccountNature;
import com.cryfirock.account.type.AccountOperational;
import com.cryfirock.account.type.AccountStatus;

/**
 * 1. DTO de respuesta para exponer una cuenta con relaciones.
 * 2. Incluye los identificadores de usuarios y productos asociados.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
public record AccountResponseDto(
        Long id,
        Long ownerId,
        AccountAssets asset,
        String currency,
        String number,
        BigDecimal balance,
        AccountNature nature,
        AccountOperational operational,
        AccountStatus status,
        Audit audit,
        List<Long> userIds,
        List<Long> productIds
) {
}

