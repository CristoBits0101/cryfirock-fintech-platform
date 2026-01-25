package com.cryfirock.account.dto;

import java.math.BigDecimal;
import java.util.List;

import com.cryfirock.account.type.AccountAssets;
import com.cryfirock.account.type.AccountNature;
import com.cryfirock.account.type.AccountOperational;
import com.cryfirock.account.type.AccountStatus;

/**
 * 1. DTO para crear o actualizar una cuenta con relaciones.
 * 2. Incluye usuarios y productos a asociar en el microservicio account.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
public record AccountRequestDto(
        Long mainOwnerId,
        AccountAssets financialAssetClass,
        String currencyCode,
        String ibanNumber,
        BigDecimal currentBalance,
        AccountNature bankAccountPurpose,
        AccountOperational bankAccountOperational,
        AccountStatus bankAccountStatus,
        List<Long> userIds,
        List<Long> productIds) {
}
