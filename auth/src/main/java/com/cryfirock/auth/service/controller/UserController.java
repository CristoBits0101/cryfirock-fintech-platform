package com.cryfirock.auth.service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.cryfirock.auth.service.entity.User;
import com.cryfirock.auth.service.service.IUserService;
import com.cryfirock.auth.service.util.ValidationUtils;

import jakarta.validation.Valid;

/**
 * ==================================================================================================================
 * Paso 13.1: Controlador que recibe solicitudes HTTP y devuelve respuestas JSON
 * ==================================================================================================================
 */

//
@RestController
// Restringe desde donde se puede hacer peticiones
@CrossOrigin(
        // Permitir peticiones de un origen concreto:Protocolo//Dominio:Puerto
        origins = "http://localhost:8082"
// Permitir un rango con comodines:
// originPatterns = "http://*.vercel.app"
)
// Prefijo de rutas del servidor
@RequestMapping("/api/users")
// Controlador de CRUD de usuarios
public class UserController {

    /**
     * ==============================================================================================================
     * Paso 13.2: Atributos
     * ==============================================================================================================
     */

    // Inyección del bean por el contenedor de Spring
    @Autowired
    // Contiene la logica para hacer CRUD de usuarios
    // Bean singleton pero los datos vienen de la BD y no de la memoria
    private IUserService userService;

    @Autowired
    private ValidationUtils ValidationUtils;

    /**
     * ==============================================================================================================
     * Paso 13.3: Métodos create
     * ==============================================================================================================
     */

    // Se ejecuta cuando se envía un método post
    @PostMapping
    // RequestBody deserializa el cuerpo de la respuesta en el objeto user
    // @Valid Activa la validación de las anotaciones de la entidad user
    // BindingResult Tiene varios métodos para comprobar los errores
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        // Comprobar si hay errores en el envío de datos
        if (result.hasErrors())
            return ValidationUtils
                    // Devolver una respuesta con los errores
                    .reportIncorrectFields(result);

        // Establece admin en falso para usuarios
        user.setAdmin(false);

        // Guarda el usuario en la base de datos
        return ResponseEntity
                // Status 201
                .status(HttpStatus.CREATED)
                // JSON Content
                .body(userService.save(user));
    }

    // Revisa si el usuario que hace la petición es admin
    // Si se cumple: El método se ejecuta normalmente.
    // Si no se cumple: 403 AccessDeniedException
    @PreAuthorize("hasRole('ADMIN')")
    // Se ejecuta cuando se envía un método post/superuser
    @PostMapping("/superuser")
    // RequestBody deserializa el cuerpo de la respuesta en el objeto user
    // @Valid Activa la validación de las anotaciones de la entidad user
    // BindingResult Tiene varios métodos para comprobar los errores
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        // Comprobar si hay errores en el envío de datos
        if (result.hasErrors())
            return ValidationUtils
                    // Devolver una respuesta con los errores
                    .reportIncorrectFields(result);

        // Establece admin en verdadero para el usuario creado
        user.setAdmin(true);

        // Guarda el usuario en la base de datos
        return ResponseEntity
                // Status 201
                .status(HttpStatus.CREATED)
                // JSON Content
                .body(userService.save(user));
    }

    /**
     * ==============================================================================================================
     * Paso 13.4: Métodos read
     * ==============================================================================================================
     */

    // Revisa si el usuario que hace la petición es admin
    // Si se cumple: El método se ejecuta normalmente.
    // Si no se cumple: 403 AccessDeniedException
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<User> getUsers() {
        // Busca los usuarios y los devuelve en la respuesta
        return userService.findAll();
    }

    // Revisa si el usuario que hace la petición es admin
    // Si se cumple: El método se ejecuta normalmente.
    // Si no se cumple: 403 AccessDeniedException
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Busca el usuario por ID
        Optional<User> userOptional = userService.findById(id);

        // Devuelve el usuario si existe o error si no se encuentra
        if (userOptional.isPresent())
            return ResponseEntity
                    // Status 200
                    .ok(userOptional.orElseThrow());

        // Devuelve error si el proceso falló
        return ResponseEntity
                // Status 404
                .notFound()
                // Patrón builder
                .build();
    }

    /**
     * ==============================================================================================================
     * Paso 13.4: Métodos update
     * ==============================================================================================================
     */

    // Revisa si el usuario que hace la petición es admin
    // Si se cumple: El método se ejecuta normalmente.
    // Si no se cumple: 403 AccessDeniedException
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    // RequestBody deserializa el cuerpo de la respuesta en el objeto user
    // @Valid Activa la validación de las anotaciones de la entidad user
    // BindingResult Tiene varios métodos para comprobar los errores
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        // Valida los parámetros que almacenan los datos del JSON enviado
        if (result.hasErrors())
            // Devolver una respuesta con los errores
            return ValidationUtils
                    .reportIncorrectFields(result);

        // Busca el usuario a actualizar
        Optional<User> userOptional = userService.findById(id);

        // Actualiza el usuario si existe
        if (userOptional.isPresent()) {
            user.setId(id);
            return ResponseEntity
                    // Status 200
                    .ok(userService.save(user));
        }

        // Devolver una respuesta con los errores
        return ResponseEntity
                // Status 404
                .notFound()
                // Patrón builder
                .build();
    }

    /**
     * ==============================================================================================================
     * Paso 13.4: Métodos delete
     * ==============================================================================================================
     */

    // Revisa si el usuario que hace la petición es admin
    // Si se cumple: El método se ejecuta normalmente.
    // Si no se cumple: 403 AccessDeniedException
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // Crea un nuevo objeto usuario
        User user = new User();

        // Establece el ID del usuario a eliminar
        user.setId(id);

        // Busca el usuario en la base de datos
        Optional<User> userOptional = userService.deleteUser(user);

        // Devuelve 200 si el usuario fue eliminado o 404 si no se encontró
        if (userOptional.isPresent())
            return ResponseEntity.ok(userOptional.orElseThrow());

        // Devuelve error si el proceso falló
        return ResponseEntity.notFound().build();
    }

}