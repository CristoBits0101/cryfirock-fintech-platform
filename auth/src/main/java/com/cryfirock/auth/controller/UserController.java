package com.cryfirock.auth.controller;

import java.util.List;

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
import com.cryfirock.auth.service.api.IUserService;
import com.cryfirock.auth.util.ValidationUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * 1. Controlador REST para la gestión de usuarios.
 * 2. Permite solicitudes CORS desde cualquier origen.
 * 3. Mapea las solicitudes a /api/users.
 *
 * @author Cristo Suárez
 * @version 1.0
 * @since 2025-01-13
 * @see <a href="https://cristo.vercel.app">cristo.vercel.app</a>
 */
@RestController @CrossOrigin @RequestMapping("/api/users")
public class UserController {
    // 1. Inyección automática del servicio.
    // 2. Servicio para operaciones relacionadas con usuarios.
    @Autowired
    private IUserService userService;

    /**
     * 1. Crea un nuevo usuario estándar.
     * 2. Mapea las solicitudes POST a /api/users.
     * 3. Valida el cuerpo de la solicitud.
     * 4. Devuelve un ResponseEntity con el usuario creado o errores de validación.
     * 5. Establece el rol de administrador en false.
     * 6. Usa BindingResult para manejar errores de validación.
     * 7. ResponseEntity permite personalizar el JSON y el estado HTTP.
     *
     * @param user El usuario a crear.
     * @param result Los resultados de la validación.
     * @return ResponseEntity<?> con el usuario creado o errores de validación.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        user.setAdmin(false);
        return (result.hasErrors())
                // 1. Verifica si hay errores de validación.
                // 2. Si hay errores reporta los campos incorrectos.
                // 3. Si no hay errores guarda el usuario y retorna un estado
                // CREATED.
                ? ValidationUtil.reportIncorrectFields(result)
                : ResponseEntity.status(HttpStatus.CREATED)
                        .body(userService.save(user));
    }

    /**
     * 1. Crea un nuevo usuario administrador.
     * 2. Mapea las solicitudes POST a /api/users/superuser.
     * 3. Valida el cuerpo de la solicitud.
     * 4. Devuelve un ResponseEntity con el usuario creado o errores de validación.
     * 5. Establece el rol de administrador en true.
     * 6. Usa BindingResult para manejar errores de validación.
     * 7. ResponseEntity permite personalizar el JSON y el estado HTTP.
     * 8. Requiere que el usuario tenga el rol ADMIN para acceder a este endpoint.
     *
     * @param user El usuario a crear.
     * @param result Los resultados de la validación.
     * @return ResponseEntity<?> con el usuario creado o errores de validación.
     */
    @PreAuthorize("hasRole('ADMIN')") @PostMapping("/superuser")
    public ResponseEntity<?> createAdmin(@Valid @RequestBody User user, BindingResult result) {
        // 1. Establece el rol de administrador en true.
        // 2. Verifica si hay errores de validación.
        // 3. Si hay errores reporta los campos incorrectos.
        // 4. Si no hay errores guarda el usuario y retorna un estado CREATED.
        user.setAdmin(true);
        return (result.hasErrors())
                ? ValidationUtil.reportIncorrectFields(result)
                : ResponseEntity.status(HttpStatus.CREATED)
                        .body(userService.save(user));
    }

    /**
     * 1. Obtiene la lista de todos los usuarios.
     * 2. Requiere rol de ADMIN o USER para acceder este endpoint.
     * 3. Mapea las solicitudes GET a /api/users.
     * 4. Devuelve una lista de usuarios.
     *
     * @return List<User> La lista de usuarios.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        // Obtiene todos los usuarios del servicio.
        List<User> users = userService.findAll();
        // Verifica si la lista está vacía.
        return users.isEmpty()
                // Si la lista está vacía retorna un estado NO_CONTENT (204).
                ? ResponseEntity.noContent().build()
                // Si la lista no está vacía retorna la lista de usuarios (200).
                : ResponseEntity.ok(users);
    }

    /**
     * 1. Obtiene un usuario por su ID.
     * 2. Requiere rol de ADMIN o USER para acceder este endpoint.
     * 3. Mapea las solicitudes GET a /api/users/{id}.
     * 4. Devuelve el usuario si se encuentra o un estado 404 si no existe.
     *
     * @param id El ID del usuario a buscar.
     * @return ResponseEntity<?> con el usuario encontrado o estado 404.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @Positive Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 1. Actualiza un usuario existente.
     * 2. Requiere rol de ADMIN o USER para acceder este endpoint.
     * 3. Mapea las solicitudes PUT a /api/users/{id}.
     * 4. Valida el cuerpo de la solicitud.
     * 5. Devuelve un ResponseEntity con el usuario o errores de validación.
     * 6. Usa BindingResult para manejar errores de validación.
     *
     * @param id El ID del usuario a actualizar.
     * @param userDto Los datos del usuario a actualizar.
     * @param result Los resultados de la validación.
     * @return ResponseEntity<?> con el usuario actualizado o errores de validación.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userDto,
            BindingResult result) {
        // 1. Verifica si hay errores de validación.
        // 2. Si hay errores reporta los campos incorrectos.
        // 3. Si no hay errores intenta actualizar el usuario.
        // 4. Si existe devuelve un ResponseEntity con el usuario actualizado.
        // 5. Si el usuario no existe lanza una excepción UserNotFoundException.
        return (result.hasErrors())
                ? ValidationUtil.reportIncorrectFields(result)
                : userService
                        .update(id, userDto)
                        .map(ResponseEntity::ok)
                        .orElseThrow(() -> new UserNotFoundException(
                                String.format("El usuario con id %d no existe",
                                        id)));
    }

    /**
     * 1. Elimina un usuario por su ID.
     * 2. Requiere rol de ADMIN o USER para acceder este endpoint.
     * 3. Mapea las solicitudes DELETE a /api/users/{id}.
     * 4. Devuelve un ResponseEntity con estado 204 No Content si se elimina.
     * 5. Devuelve un ResponseEntity con estado 404 Not Found si no existe.
     *
     * @param id El ID del usuario a eliminar.
     * @return ResponseEntity<?> con estado 204 o 404.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')") @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // 1. Intenta eliminar el usuario por ID.
        // 2. Si se elimina devuelve un ResponseEntity con estado 204 No Content.
        // 3. Si no existe devuelve un ResponseEntity con estado 404 Not Found.
        return userService
                // Intenta eliminar el usuario por ID.
                .deleteById(id)
                // Si se elimina devuelve un ResponseEntity con estado 204 No
                // Content en JSON.
                .map(user -> ResponseEntity.noContent().build())
                // Si no existe devuelve un ResponseEntity con estado 404 Not Found
                // en JSON.
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
