package com.cryfirock.account.service.api;

import java.util.List;

import com.cryfirock.account.dto.AccountRequestDto;
import com.cryfirock.account.dto.AccountResponseDto;

/**
 * 1. Contrato para operaciones CRUD de cuentas y relaciones asociadas.
 * 2. Expone consultas por usuario para recuperar cuentas vinculadas.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
public interface IAccountService {
    /**
     * 1. Crea una cuenta junto con sus relaciones de usuarios y productos.
     *
     * @param request Datos de la cuenta y relaciones.
     * @return Cuenta creada con relaciones.
     */
    AccountResponseDto create(AccountRequestDto request);

    /**
     * 1. Actualiza una cuenta y reemplaza sus relaciones.
     *
     * @param id Identificador de la cuenta.
     * @param request Datos actualizados de la cuenta y relaciones.
     * @return Cuenta actualizada con relaciones.
     */
    AccountResponseDto update(Long id, AccountRequestDto request);

    /**
     * 1. Obtiene una cuenta por identificador.
     * 2. Incluye usuarios y productos asociados.
     *
     * @param id Identificador de la cuenta.
     * @return Cuenta con relaciones.
     */
    AccountResponseDto findById(Long id);

    /**
     * 1. Obtiene las cuentas asociadas a un usuario.
     * 2. Incluye los productos asociados a cada cuenta.
     *
     * @param userId Identificador del usuario.
     * @return Lista de cuentas con relaciones.
     */
    List<AccountResponseDto> findByUserId(Long userId);

    /**
     * 1. Elimina una cuenta y sus relaciones.
     *
     * @param id Identificador de la cuenta.
     */
    void delete(Long id);
}

