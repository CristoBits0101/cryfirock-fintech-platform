package com.cryfirock.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryfirock.account.entity.Account;

/**
 * 1. Repositorio JPA para la entidad Account.
 * 2. Permite operaciones CRUD sobre la tabla account.
 * 3. Expone consultas básicas por Spring Data.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
public interface JpaAccountRepository extends JpaRepository<Account, Long> {
}
