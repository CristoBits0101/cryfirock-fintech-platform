package com.cryfirock.accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cryfirock.accounts.dto.AccountCreateDto;
import com.cryfirock.accounts.dto.AccountResponseDto;
import com.cryfirock.accounts.entity.Account;

/**
 * 1. Mapper de MapStruct para la entidad Account.
 * 2. Convierte entre entidades y DTOs.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
    /**
     * 1. Convierte un DTO de creación a entidad.
     * 2. Ignora campos generados automáticamente.
     *
     * @param dto DTO de creación.
     * @return Entidad Account.
     */
    @Mapping(target = "id", ignore = true) @Mapping(target = "accountNumber", ignore = true) @Mapping(target = "balance", ignore = true) @Mapping(target = "status", ignore = true) @Mapping(target = "audit", ignore = true)
    Account toEntity(AccountCreateDto dto);

    /**
     * 1. Convierte una entidad a DTO de respuesta.
     * 2. Mapea los campos de auditoría.
     *
     * @param entity Entidad Account.
     * @return DTO de respuesta.
     */
    @Mapping(target = "createdAt", source = "audit.createdAt") @Mapping(target = "updatedAt", source = "audit.updatedAt")
    AccountResponseDto toResponseDto(Account entity);
}
