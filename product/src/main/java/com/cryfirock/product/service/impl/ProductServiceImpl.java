package com.cryfirock.product.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.repository.JpaProductRepository;
import com.cryfirock.product.service.api.IProductService;

/**
 * 1. Implementación de la interfaz IProductService.
 * 2. Maneja operaciones CRUD para productos.
 * 3. Gestiona transacciones de lectura y escritura.
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
     * {@inheritDoc}
     */
    @Override @Transactional
    public Product save(@NonNull Product product) {
        Objects.requireNonNull(product, "Product must not be null");
        return productRepository.save(product);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional(readOnly = true)
    public Optional<Product> findById(@NonNull Long id) {
        Objects.requireNonNull(id, "ID must not be null");
        return productRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public Optional<Product> update(@NonNull Long id, @NonNull Product product) {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(product, "Product must not be null");
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCode(product.getCode());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setStatus(product.getStatus());
            return productRepository.save(existingProduct);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override @Transactional
    public Optional<Product> deleteById(@NonNull Long id) {
        Objects.requireNonNull(id, "ID must not be null");
        return productRepository.findById(id).map(existingProduct -> {
            productRepository.deleteById(id);
            return existingProduct;
        });
    }
}
