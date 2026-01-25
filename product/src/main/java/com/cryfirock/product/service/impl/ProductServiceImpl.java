package com.cryfirock.product.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.repository.JpaProductRepository;
import com.cryfirock.product.service.api.IProductService;

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

    @Override @Transactional
    public Optional<Product> update(
            @NotNull Long id,
            @NotNull Product product) {
        return productRepository
                .findById(id)
                .map(
                        existingProduct -> {
                            existingProduct.setName(product.getName());
                            existingProduct.setDescription(product.getDescription());
                            existingProduct.setCode(product.getCode());
                            existingProduct.setCategory(product.getCategory());
                            existingProduct.setStatus(product.getStatus());
                            return productRepository.save(existingProduct);
                        });
    }

    @Override @Transactional
    public Optional<Product> deleteById(@NotNull Long id) {
        return productRepository
                .findById(id)
                .map(
                        existingProduct -> {
                            productRepository.delete(existingProduct);
                            return existingProduct;
                        });
    }
}
