package com.cryfirock.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryfirock.account.entity.AccountUser;

/**
 * 1. Repositorio JPA para relaciones de cuentas con usuarios.
 * 2. Permite operaciones CRUD sobre la tabla account_user.
 * 3. Expone consultas básicas por Spring Data.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
public interface JpaAccountUserRepository extends JpaRepository<AccountUser, Long> {
    /**
     * Encuentra las cuentas asociadas a un usuario.
     *
     * @param userId Identificador del usuario en auth.
     * @return Lista de relaciones cuenta usuario.
     */
    List<AccountUser> findAllByUserId(Long userId);

    /**
     * Encuentra los usuarios asociados a una cuenta.
     *
     * @param accountId Identificador de la cuenta.
     * @return Lista de relaciones cuenta usuario.
     */
    List<AccountUser> findAllByAccountId(Long accountId);

    /**
     * 1. Elimina las relaciones de cuenta usuario asociadas y cuenta de banco.
     * 2. Se usa el id de la cuenta para eliminar las relaciones.
     * 3. Utilizando la eliminación en cascada.
     *
     * @param accountId Identificador de la cuenta.
     */
    void deleteAllByAccountId(Long accountId);

    /**
     * 1. Elimina las relaciones de cuenta usuario asociadas y cuenta de banco.
     * 2. Se usa el id del usuario para eliminar las relaciones.
     * 3. Utilizando la eliminación en cascada.
     *
     * @param userId Identificador del usuario en auth.
     */
    void deleteAllByUserId(Long userId);
}
