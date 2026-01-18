package com.cryfirock.accounts.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryfirock.accounts.entity.Account;
import com.cryfirock.accounts.model.AccountNature;
import com.cryfirock.accounts.model.AccountStatus;

/**
 * 1. Repositorio JPA para la entidad Account.
 * 2. Proporciona operaciones CRUD y consultas personalizadas.
 * 3. Extiende JpaRepository para heredar métodos estándar.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-18
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long> {
    /**
     * 1. Busca una cuenta por su número único.
     * 2. Retorna un Optional vacío si no existe.
     *
     * @param accountNumber Número de cuenta a buscar.
     * @return Optional con la cuenta encontrada o vacío.
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * 1. Busca todas las cuentas de un propietario.
     * 2. Retorna lista vacía si no tiene cuentas.
     *
     * @param ownerId Identificador del propietario.
     * @return Lista de cuentas del propietario.
     */
    List<Account> findByOwnerId(Long ownerId);

    /**
     * 1. Busca cuentas de un propietario por estado.
     * 2. Filtra por propietario y estado de la cuenta.
     *
     * @param ownerId Identificador del propietario.
     * @param status Estado de la cuenta.
     * @return Lista de cuentas filtradas.
     */
    List<Account> findByOwnerIdAndStatus(Long ownerId, AccountStatus status);

    /**
     * 1. Busca cuentas de un propietario por naturaleza.
     * 2. Filtra por propietario y naturaleza de la cuenta.
     *
     * @param ownerId Identificador del propietario.
     * @param nature Naturaleza de la cuenta.
     * @return Lista de cuentas filtradas.
     */
    List<Account> findByOwnerIdAndNature(Long ownerId, AccountNature nature);

    /**
     * 1. Verifica si existe una cuenta con el número dado.
     * 2. Retorna true si existe, false si no.
     *
     * @param accountNumber Número de cuenta a verificar.
     * @return true si existe, false si no.
     */
    boolean existsByAccountNumber(String accountNumber);
}
