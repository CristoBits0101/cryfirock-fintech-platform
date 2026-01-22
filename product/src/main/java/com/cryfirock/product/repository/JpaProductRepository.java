package com.cryfirock.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cryfirock.product.entity.Product;

/**
 * Repositorio JPA para la entidad Product.
 * Proporciona operaciones CRUD y consultas personalizadas para productos financieros.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-22
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {
}
