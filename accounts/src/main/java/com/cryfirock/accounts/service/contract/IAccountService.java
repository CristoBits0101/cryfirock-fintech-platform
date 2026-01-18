package com.cryfirock.accounts.service.contract;

import java.util.List;
import java.util.Optional;

import com.cryfirock.accounts.dto.AccountCreateDto;
import com.cryfirock.accounts.dto.AccountResponseDto;
import com.cryfirock.accounts.dto.AccountUpdateDto;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountStatus;

/**
 * 1. Interfaz que define las operaciones del servicio de cuentas.
 * 2. Proporciona métodos para gestionar cuentas financieras.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public interface IAccountService {
    /**
     * 1. Crea una nueva cuenta.
     * 2. Genera automáticamente el número de cuenta.
     *
     * @param dto Datos para crear la cuenta.
     * @return DTO con los datos de la cuenta creada.
     */
    AccountResponseDto create(AccountCreateDto dto);

    /**
     * 1. Busca una cuenta por su ID.
     * 2. Retorna Optional vacío si no existe.
     *
     * @param id Identificador de la cuenta.
     * @return Optional con el DTO de la cuenta o vacío.
     */
    Optional<AccountResponseDto> findById(Long id);

    /**
     * 1. Busca una cuenta por su número único.
     * 2. Retorna Optional vacío si no existe.
     *
     * @param accountNumber Número de cuenta.
     * @return Optional con el DTO de la cuenta o vacío.
     */
    Optional<AccountResponseDto> findByAccountNumber(String accountNumber);

    /**
     * 1. Obtiene todas las cuentas de un propietario.
     * 2. Retorna lista vacía si no tiene cuentas.
     *
     * @param ownerId Identificador del propietario.
     * @return Lista de DTOs de cuentas.
     */
    List<AccountResponseDto> findByOwnerId(Long ownerId);

    /**
     * 1. Obtiene cuentas de un propietario filtradas por estado.
     * 2. Retorna lista vacía si no hay coincidencias.
     *
     * @param ownerId Identificador del propietario.
     * @param status Estado de la cuenta.
     * @return Lista de DTOs de cuentas filtradas.
     */
    List<AccountResponseDto> findByOwnerIdAndStatus(Long ownerId, AccountStatus status);

    /**
     * 1. Obtiene cuentas de un propietario filtradas por naturaleza.
     * 2. Retorna lista vacía si no hay coincidencias.
     *
     * @param ownerId Identificador del propietario.
     * @param nature Naturaleza de la cuenta.
     * @return Lista de DTOs de cuentas filtradas.
     */
    List<AccountResponseDto> findByOwnerIdAndNature(Long ownerId, AccountNature nature);

    /**
     * 1. Actualiza el estado de una cuenta existente.
     * 2. Lanza excepción si la cuenta no existe.
     *
     * @param id Identificador de la cuenta.
     * @param dto Datos para actualizar.
     * @return DTO con los datos de la cuenta actualizada.
     */
    AccountResponseDto update(Long id, AccountUpdateDto dto);

    /**
     * 1. Obtiene todas las cuentas del sistema.
     * 2. Solo para uso administrativo.
     *
     * @return Lista de DTOs de todas las cuentas.
     */
    List<AccountResponseDto> findAll();
}
