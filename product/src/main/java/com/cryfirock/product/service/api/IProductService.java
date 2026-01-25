package com.cryfirock.product.service.api;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;

import com.cryfirock.product.entity.Product;

/**
 * 1. Interfaz que define las operaciones CRUD para productos.
 * 2. Proporciona métodos de creación, lectura, actualización y eliminación.
 * 3. Todas las implementaciones deben garantizar la validación de nulos.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-22
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public interface IProductService {
    // ============================================================================================
    // --- Métodos de creación ---
    // ============================================================================================

    /**
     * 1. Crea un producto.
     * 2. Guarda el producto en la base de datos.
     *
     * @param product Producto a crear.
     * @return Producto creado con su ID asignado.
     */
    Product save(@NonNull Product product);

    // ============================================================================================
    // --- Métodos de lectura ---
    // ============================================================================================

    /**
     * 1. Obtiene todos los productos.
     * 2. Retorna una lista de productos.
     *
     * @return Lista de productos encontrados.
     */
    List<Product> findAll();

    /**
     * 1. Busca un producto por su ID.
     * 2. Retorna el producto si se encuentra, o Optional vacío si no.
     *
     * @param id ID del producto a buscar.
     * @return Optional con el producto encontrado o vacío si no existe.
     */
    Optional<Product> findById(@NonNull Long id);

    // ============================================================================================
    // --- Métodos de actualización ---
    // ============================================================================================

    /**
     * 1. Actualiza un producto existente.
     * 2. Guarda los cambios en la base de datos.
     *
     * @param id ID del producto a actualizar.
     * @param product Datos del producto a actualizar.
     * @return Optional con el producto actualizado o vacío si no existe.
     */
    Optional<Product> update(@NonNull Long id, @NonNull Product product);

    // ============================================================================================
    // --- Métodos de eliminación ---
    // ============================================================================================

    /**
     * 1. Elimina un producto por su ID.
     * 2. Elimina el producto de la base de datos.
     *
     * @param id ID del producto a eliminar.
     * @return Optional con el producto eliminado o vacío si no existía.
     */
    Optional<Product> deleteById(@NonNull Long id);
}
