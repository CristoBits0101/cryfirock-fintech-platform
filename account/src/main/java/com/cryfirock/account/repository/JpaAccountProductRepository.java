package com.cryfirock.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cryfirock.account.entity.AccountProduct;

/**
 * 1. Repositorio JPA para relaciones de cuentas con productos.
 * 2. Permite operaciones CRUD sobre la tabla account_product.
 * 3. Expone consultas básicas por Spring Data.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 */
public interface JpaAccountProductRepository extends JpaRepository<AccountProduct, Long> {
    /**
     * 1. Obtiene las relaciones de producto por identificador de cuenta.
     * 2. Permite recuperar productos asociados a una cuenta.
     *
     * @param accountId Identificador de la cuenta.
     * @return Lista de relaciones cuenta producto.
     */
    java.util.List<AccountProduct> findAllByAccountId(Long accountId);

    /**
     * 1. Elimina las relaciones de producto asociadas a una cuenta.
     * 2. Se usa para reemplazar relaciones en actualizaciones.
     *
     * @param accountId Identificador de la cuenta.
     */
    void deleteAllByAccountId(Long accountId);
}
