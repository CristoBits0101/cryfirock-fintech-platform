package com.cryfirock.auth.controller;

import com.cryfirock.auth.dto.UserUpdateDto;
import com.cryfirock.auth.entity.User;
import com.cryfirock.auth.exception.UserNotFoundException;
import com.cryfirock.auth.service.IUserService;
import com.cryfirock.auth.util.ValidationUtils;
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

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  private IUserService userService;
  @Autowired
  private ValidationUtils validationUtils;

  @PostMapping
  public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
    if (result.hasErrors())
      return validationUtils.reportIncorrectFields(result);
    user.setAdmin(false);
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/superuser")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
    if (result.hasErrors())
      return validationUtils.reportIncorrectFields(result);
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
    if (userOptional.isPresent())
      return ResponseEntity.ok(userOptional.orElseThrow());
    return ResponseEntity.notFound().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(
      @PathVariable Long id, @Valid @RequestBody UserUpdateDto userDto, BindingResult result) {
    if (result.hasErrors())
      return validationUtils.reportIncorrectFields(result);
    return userService
        .update(id, userDto)
        .map(ResponseEntity::ok)
        .orElseThrow(
            () -> new UserNotFoundException(String.format("El usuario con id %d no existe", id)));
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
