package com.cryfirock.auth.controller;

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

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.exception.UserNotFoundException;
import com.cryfirock.auth.service.IUserService;
import com.cryfirock.auth.util.ValidationUtil;

import jakarta.validation.Valid;

/**
 * 006: Controlador API que permite solicitudes desde todos los orígenes a /api/users
 */
@RestController
@CrossOrigin
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
   * @param result de la validación
   * @return ResponseEntity con errores de validación o 201 y el usuario creado
   */
  @PostMapping
  public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
    // Valida los parámetros que almacenan los datos del JSON enviado
    if (result.hasErrors()) return ValidationUtil.reportIncorrectFields(result);
    // Establece admin a falso por defecto para el usuario creado
    user.setAdmin(false);
    // Guarda el usuario en la base de datos
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
  }

  /**
   * Permite crear un nuevo administrador
   * 
   * @param user   el nuevo administrador
   * @param result de la validación
   * @return ResponseEntity con errores de validación o 201 y el usuario creado
   */
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/superuser")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
    // Valida los parámetros que almacenan los datos del JSON enviado
    if (result.hasErrors()) return ValidationUtil.reportIncorrectFields(result);
    // Establece admin a verdadero para el usuario creado
    user.setAdmin(true);
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping
  public List<User> getUsers() {
    return userService.findAll();
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> userOptional = userService.findById(id);
    if (userOptional.isPresent())return ResponseEntity.ok(userOptional.orElseThrow());
    return ResponseEntity.notFound().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(
      @PathVariable Long id,
      @Valid @RequestBody UserUpdateDto userDto,
      BindingResult result) {
    if (result.hasErrors()) return ValidationUtil.reportIncorrectFields(result);
    return userService
        .update(id, userDto)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new UserNotFoundException(String.format("El usuario con id %d no existe", id)));
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    return userService
        .deleteById(id)
        .map(user -> ResponseEntity.noContent().build())
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
