package com.cryfirock.account.repository;

import java.util.List;

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
     * Encuentra los productos asociados a una cuenta.
     *
     * @param accountId Identificador de la cuenta.
     * @return Lista de relaciones cuenta producto.
     */
    List<AccountProduct> findAllByAccountId(Long accountId);

    /**
     * Encuentra las cuentas asociadas a un producto.
     *
     * @param productId Identificador del producto.
     * @return Lista de relaciones cuenta producto.
     */
    List<AccountProduct> findAllByProductId(Long productId);

    /**
     * 1. Elimina las asociaciones de cuenta con productos.
     * 2. Se usa el id de la cuenta para eliminar las relaciones.
     * 3. Utilizando la eliminación en cascada.
     *
     * @param accountId Identificador de la cuenta.
     */
    void deleteAllByAccountId(Long accountId);

    /**
     * 1. Elimina las asociaciones de cuenta con productos.
     * 2. Se usa el id del producto para eliminar las relaciones.
     * 3. Utilizando la eliminación en cascada.
     *
     * @param productId Identificador del producto.
     */
    void deleteAllByProductId(Long productId);
}
