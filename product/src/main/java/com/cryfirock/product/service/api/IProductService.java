package com.cryfirock.product.service.api;

import java.util.List;
import java.util.Optional;

import com.cryfirock.product.entity.Product;

import jakarta.validation.constraints.NotNull;

public interface IProductService {
    // ============================================================================================
    // Métodos de creación
    // ============================================================================================
    /**
     * 1. Crea un producto.
     * 2. Guarda el producto en la base de datos.
     *
     * @param product Producto a crear.
     */
    Product save(@NotNull Product product);

    // ============================================================================================
    // Métodos de lectura
    // ============================================================================================
    /**
     * 1. Obtiene todos los productos.
     * 2. Retorna una lista de productos.
     *
     * @return List<Product> Lista de productos.
     */
    List<Product> findAll();

    /**
     * 1. Busca un producto por su ID.
     * 2. Retorna el producto si se encuentra, o Optional vacío si no.
     *
     * @param id ID del producto a buscar.
     * @return Optional<Product> Producto encontrado.
     */
    Optional<Product> findById(@NotNull Long id);

    // ============================================================================================
    // Métodos de actualización
    // ============================================================================================
    /**
     * 1. Actualiza un producto.
     * 2. Guarda el producto en la base de datos.
     *
     * @param product Producto a actualizar.
     * @return Optional<Product> Producto actualizado.
     */
    Optional<Product> update(@NotNull Long id, @NotNull Product product);

    // ============================================================================================
    // Métodos de eliminación
    // ============================================================================================
    /**
     * 1. Elimina un producto por su ID.
     * 2. Elimina el producto de la base de datos.
     *
     * @param id ID del producto a eliminar.
     * @return Optional<Product> Producto eliminado.
     */
    Optional<Product> deleteById(@NotNull Long id);
}
