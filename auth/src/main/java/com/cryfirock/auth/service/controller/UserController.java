package com.cryfirock.auth.service.controller;

import com.cryfirock.auth.service.entity.User;
import com.cryfirock.auth.service.service.IUserService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

// Controlador API que permite solicitudes desde todos los orígenes a /api/users
@RestController
@CrossOrigin(origins="http://localhost:8082", originPatterns = "*")
@RequestMapping("/api/users")
public class UserController {

    /**
     * Atributos
     */
    @Autowired
    private IUserService userService;

    /**
     * Permite crear un nuevo usuario
     * 
     * @param user el nuevo usuario
     * @param result resultado de la validación
     * @return ResponseEntity con errores de validación o 201 con el usuario creado
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        // Valida los parámetros que almacenan los datos del JSON enviado
        if (result.hasErrors())
            return validation(result);

        // Establece admin en falso por defecto para el usuario creado
        user.setAdmin(false);

        // Guarda el usuario en la base de datos
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    /**
     * Permite crear un nuevo administrador
     * 
     * @param user   el nuevo administrador
     * @param result resultado de la validación
     * @return ResponseEntity con errores de validación o 201 con el usuario creado
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/superuser")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        // Valida los parámetros que almacenan los datos del JSON enviado
        if (result.hasErrors())
            return validation(result);

        // Establece admin en verdadero para el usuario creado
        user.setAdmin(true);

        // Guarda el usuario en la base de datos
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    /**
     * Permite actualizar un usuario
     * 
     * @param id   el id del usuario
     * @param user el usuario a actualizar
     * @return ResponseEntity con errores de validación, 200 si se actualiza o 404 si hay error
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        // Valida los parámetros que almacenan los datos del JSON enviado
        if (result.hasErrors())
            return validation(result);

        // Busca el usuario a actualizar
        Optional<User> userOptional = userService.findById(id);

        // Actualiza el usuario si existe
        if (userOptional.isPresent()) {
            user.setId(id);
            return ResponseEntity.ok(userService.save(user));
        }

        // Devuelve error si el proceso falló
        return ResponseEntity.notFound().build();
    }

    /**
     * Obtiene todos los usuarios
     * 
     * @return Lista de todos los usuarios
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<User> getUsers() {
        // Busca los usuarios y los devuelve en la respuesta
        return userService.findAll();
    }

    /**
     * Obtiene un usuario por ID
     * 
     * @param id ID del usuario
     * @return ResponseEntity con el usuario y estado 200 o 404 si no se encuentra
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // Busca el usuario por ID
        Optional<User> userOptional = userService.findById(id);

        // Devuelve el usuario si existe o error si no se encuentra
        if (userOptional.isPresent())
            return ResponseEntity.ok(userOptional.orElseThrow());

        // Devuelve error si el proceso falló
        return ResponseEntity.notFound().build();
    }

    /**
     * Permite eliminar un usuario
     * 
     * @param id ID del usuario a eliminar
     * @return ResponseEntity con 200 si se elimina o 404 si no se encuentra
     */
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

    /**
     * Crea el mensaje de validación
     * 
     * @param result contiene los campos
     * @return el mensaje de validación
     */
    private ResponseEntity<?> validation(BindingResult result) {
        // Crea un mapa para almacenar los errores de validación y sus mensajes
        Map<String, String> errors = new HashMap<>();

        // Recorre cada campo con errores de validación y los agrega al mapa
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });

        // Devuelve los errores de validación con código de estado 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}