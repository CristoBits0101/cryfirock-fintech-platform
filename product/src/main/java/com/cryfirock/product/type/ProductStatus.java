package com.cryfirock.product.type;

/**
 * Enum que representa el estado de un producto asociado a una cuenta.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-21
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
public enum ProductStatus {
    ACTIVE, // Producto activo en la cuenta.
    SUSPENDED, // Producto suspendido temporalmente.
    CLOSED // Producto cerrado o inactivo definitivamente.
}
