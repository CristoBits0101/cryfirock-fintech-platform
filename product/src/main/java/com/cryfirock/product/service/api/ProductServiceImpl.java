package com.cryfirock.product.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.repository.JpaProductRepository;
import com.cryfirock.product.service.impl.IProductService;

import jakarta.validation.constraints.NotNull;

/**
 * Implementación de la interfaz IProductService.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-22
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@Service
public class ProductServiceImpl implements IProductService {
    // Repositorio JPA para productos.
    private final JpaProductRepository productRepository;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param productRepository Repositorio JPA para productos.
     */
    public ProductServiceImpl(JpaProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 1. Guarda un producto en la base de datos.
     * 2. Marca la transacción como de escritura.
     * 3. Suprime las advertencias de nulidad.
     * 4. Devuelve un Optional que puede contener el producto guardado.
     *
     * @param product Producto a guardar.
     * @return Optional con el producto guardado si se encuentra o vacío si no.
     */
    @Override @Transactional
    public Product save(@NotNull Product product) {
        return Optional.ofNullable(product)
                .map(productRepository::save)
                .orElseThrow(() -> new IllegalArgumentException("Product must not be null"));
    }

    @Override @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override @Transactional(readOnly = true)
    public Optional<Product> findById(@NotNull Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> update(@NotNull Long id, @NotNull Product product) {
        return null;
    }

    @Override
    public Optional<Product> deleteById(@NotNull Long id) {
        return null;
    }
}
