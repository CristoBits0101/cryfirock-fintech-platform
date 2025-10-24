package com.creativadigital360.api.core.controller;

import com.creativadigital360.api.core.dto.UserUpdateDto;
import com.creativadigital360.api.core.entity.User;
import com.creativadigital360.api.core.exception.UserNotFoundException;
import com.creativadigital360.api.core.service.IUserService;
import com.creativadigital360.api.core.util.ValidationUtils;

import jakarta.validation.Valid;

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

/**
 * ==================================================================================================================
 * Paso 13.1: Controlador que recibe solicitudes HTTP y devuelve respuestas JSON
 * ==================================================================================================================
 */

//
@RestController
// Restringe desde donde se puede hacer peticiones
@CrossOrigin
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
    private ValidationUtils validationUtils;

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
            return validationUtils
                    // Devolver una respuesta con los errores
                    .reportIncorrectFields(result);

        // Establece admin en falso para usuarios
        user.setAdmin(false);

        // Guarda el usuario en la base de datos
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(user));
    }

    // Limitado a usuarios con rol administrador
    @PreAuthorize("hasRole('ADMIN')")
    // Endpoint para crear administradores
    @PostMapping("/superuser")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors())
            return validationUtils
                    .reportIncorrectFields(result);

        // Establece admin en verdadero para super usuarios
        user.setAdmin(true);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(user));
    }

    /**
     * ==============================================================================================================
     * Paso 13.4: Métodos read
     * ==============================================================================================================
     */

    // Endpoint protegido que devuelve todos los usuarios
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<User> getUsers() {
        return userService.findAll();
    }

    // Endpoint protegido que devuelve un usuario por id
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Busca el usuario por id
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent())
            return ResponseEntity
                    .ok(userOptional.orElseThrow());

        // Si no existe devuelve 404
        return ResponseEntity
                .notFound()
                .build();
    }

    /**
     * ==============================================================================================================
     * Paso 13.5: Métodos update
     * ==============================================================================================================
     */

    // Endpoint protegido que actualiza usuarios
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDto userDto, BindingResult result)
    {
        if (result.hasErrors())
            return validationUtils
                    .reportIncorrectFields(result);

        // Actualiza el usuario y devuelve 404 si no existe
        return userService
                .update(id, userDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException(String.format("El usuario con id %d no existe", id)));
    }

    /**
     * ==============================================================================================================
     * Paso 13.6: Métodos delete
     * ==============================================================================================================
     */

    // Endpoint protegido que elimina usuarios
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService
                .deleteById(id)
                // Si lo elimina devuelve 204
                .map(user -> ResponseEntity.noContent().build())
                // Si no existe devuelve 404
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
