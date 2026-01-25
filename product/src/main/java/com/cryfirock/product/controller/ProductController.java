package com.cryfirock.product.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cryfirock.product.entity.Product;
import com.cryfirock.product.service.api.IProductService;
import com.cryfirock.product.util.ValidationUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * 1. Controlador REST para la gestión de productos.
 * 2. Permite solicitudes CORS desde cualquier origen.
 * 3. Mapea las solicitudes a /api/products.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2026-01-24
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestController @CrossOrigin @RequestMapping("/api/products")
public class ProductController {
    // Servicio para operaciones relacionadas con productos.
    private final IProductService productService;

    /**
     * Constructor que inyecta las dependencias necesarias.
     *
     * @param productService Servicio de productos.
     */
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * 1. Crea un nuevo producto.
     * 2. Mapea las solicitudes POST a /api/products.
     * 3. Valida el cuerpo de la solicitud.
     * 4. Devuelve un ResponseEntity con el producto creado o errores de validación.
     *
     * @param product El producto a crear.
     * @param result Los resultados de la validación.
     * @return ResponseEntity<?> con el producto creado o errores de validación.
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product,
            BindingResult result) {
        Objects.requireNonNull(product, "Product must not be null");
        return (result.hasErrors())
                ? ValidationUtil.reportIncorrectFields(result)
                : ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    /**
     * 1. Obtiene la lista de todos los productos.
     * 2. Mapea las solicitudes GET a /api/products.
     * 3. Devuelve una lista de productos.
     *
     * @return ResponseEntity<List<Product>> La lista de productos.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.findAll();
        return products.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(products);
    }

    /**
     * 1. Obtiene un producto por su ID.
     * 2. Mapea las solicitudes GET a /api/products/{id}.
     * 3. Devuelve el producto si se encuentra o un estado 404 si no existe.
     *
     * @param id El ID del producto a buscar.
     * @return ResponseEntity<Product> con el producto encontrado o estado 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable @Positive Long id) {
        Objects.requireNonNull(id, "ID must not be null");
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 1. Actualiza un producto existente.
     * 2. Mapea las solicitudes PUT a /api/products/{id}.
     * 3. Valida el cuerpo de la solicitud.
     * 4. Devuelve un ResponseEntity con el producto actualizado o errores de validación.
     *
     * @param id El ID del producto a actualizar.
     * @param product Los datos del producto a actualizar.
     * @param result Los resultados de la validación.
     * @return ResponseEntity<?> con el producto actualizado o errores de validación.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product,
            BindingResult result) {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(product, "Product must not be null");
        if (result.hasErrors()) {
            return ValidationUtil.reportIncorrectFields(result);
        }
        return productService.update(id, product)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 1. Elimina un producto por su ID.
     * 2. Mapea las solicitudes DELETE a /api/products/{id}.
     * 3. Devuelve un ResponseEntity con estado 204 No Content si se elimina.
     * 4. Devuelve un ResponseEntity con estado 404 Not Found si no existe.
     *
     * @param id El ID del producto a eliminar.
     * @return ResponseEntity<?> con estado 204 o 404.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Objects.requireNonNull(id, "ID must not be null");
        return productService.deleteById(id)
                .map(p -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
