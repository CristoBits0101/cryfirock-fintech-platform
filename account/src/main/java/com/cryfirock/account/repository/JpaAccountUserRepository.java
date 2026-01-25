package com.cryfirock.account.repository;

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
     * 1. Obtiene las relaciones de cuenta por identificador de usuario.
     * 2. Permite recuperar cuentas asociadas a un usuario específico.
     *
     * @param userId Identificador del usuario en auth.
     * @return Lista de relaciones cuenta usuario.
     */
    java.util.List<AccountUser> findAllByUserId(Long userId);

    /**
     * 1. Obtiene las relaciones de usuario por identificador de cuenta.
     * 2. Permite recuperar usuarios asociados a una cuenta.
     *
     * @param accountId Identificador de la cuenta.
     * @return Lista de relaciones cuenta usuario.
     */
    java.util.List<AccountUser> findAllByAccountId(Long accountId);

    /**
     * 1. Elimina las relaciones de usuario asociadas a una cuenta.
     * 2. Se usa para reemplazar relaciones en actualizaciones.
     *
     * @param accountId Identificador de la cuenta.
     */
    void deleteAllByAccountId(Long accountId);
}
